package com.hugey.scattools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hugey.scattools.EditableList.EditableListView;
import com.hugey.scattools.List.ListView;
import com.hugey.scattools.Scat.ScatDie;
import com.hugey.scattools.Scat.ScatTimer;
import com.hugey.scattools.Scat.ScatView;
import com.hugey.scattools.Settings.Settings;
import com.hugey.scattools.Settings.SettingsView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScatView.ScatViewListener, ListView.ListViewListener, EditableListView.EditableListViewListener, ScatTimer.TimerView, ScatDie.DieView, MenuItem.OnMenuItemClickListener {

    private Button mBtnList;
    private Button mBtnTools;
    private Button mBtnEditable;

    private boolean mIsTicking = false;
    private boolean mIsRolling = false;

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    private ScatTimer mTimer;
    private ScatDie mDie;

    private String mDieText;
    private String mTimerText = Settings.TIMER_DURATION_230;
    private int mTickingState = 0;
    //local Settings copy for applying changes
    private Settings mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = new ScatTimer(this);
        mTimer.setTimerView(this);
        mDie = new ScatDie(this, getResources().getStringArray(R.array.letters));

        //reset settings each time you restart the app
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SettingsView.KEY_ALPHABET);
        editor.remove(SettingsView.KEY_EXPIRE);
        editor.remove(SettingsView.KEY_SKIP);
        editor.remove(SettingsView.KEY_TIMER);
        editor.remove(SettingsView.KEY_TICK);
        editor.apply();

        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, true);

        mSettings = new Settings();

        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);

        mPagerAdapter.setScatListener(this);
        mPagerAdapter.setListListener(this);
        mPagerAdapter.setEditableListListener(this);


        mBtnList = (Button) findViewById(R.id.btn_list);
        mBtnTools = (Button) findViewById(R.id.btn_tools);
        mBtnEditable = (Button) findViewById(R.id.btn_editable);

        mBtnList.setOnClickListener(this);
        mBtnTools.setOnClickListener(this);
        mBtnEditable.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.REQUEST_CODE) {
            Settings settings = data.getParcelableExtra(SettingsActivity.EXTRA_SETTINGS_OUT);

            //settings have changed, need to apply changes
            if (!mSettings.equals(settings)) {
                //see if we need to switch the alphabet on the ScatDie
                if (mSettings.isScatAlphabet() && !settings.isScatAlphabet()) {
                    mDie.removeDieCallbacks();
                    mDie.setLetters(getResources().getStringArray(R.array.letters_full));
                    mDie.resetCurrentLetter();
                    mIsRolling = false;
                } else if (!mSettings.isScatAlphabet() && settings.isScatAlphabet()) {
                    mDie.removeDieCallbacks();
                    mDie.setLetters(getResources().getStringArray(R.array.letters));
                    mDie.resetCurrentLetter();
                    mIsRolling = false;
                }
                //see if we need to skip the previous letter or not
                if (mSettings.getSkipPrevious() != settings.getSkipPrevious()) {
                    mDie.setSkipPrevious(settings.getSkipPrevious());
                }
                //see if the timer duration has changed
                if (!TextUtils.equals(mSettings.getTimerDuration(), settings.getTimerDuration())) {
                    mTimer.removeTimerCallbacks();
                    mTimer.setTimerDuration(settings.getTimerDuration());
                    mTimer.resetTimerProgress();



                    mIsTicking = false;
                }


                //update our new settings value
                mSettings = settings;
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_tools) {
            mViewPager.setCurrentItem(0);
        }
        if (view.getId() == R.id.btn_list) {
            mViewPager.setCurrentItem(1);
        }
        if (view.getId() == R.id.btn_editable) {
            mViewPager.setCurrentItem(2);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.mn_options);
        menuItem.setIcon(android.R.drawable.ic_dialog_info);
        menuItem.setVisible(true);
        menuItem.setOnMenuItemClickListener(this);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scat_menu, menu);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Intent settingsIntent = SettingsActivity.getLaunchIntent(this, mSettings);


        startActivityForResult(settingsIntent, SettingsActivity.REQUEST_CODE);
        return false;
    }

    /**
     * @link ScatView.ScatViewListener
     **/
    @Override
    public void onPlayClicked() {
        if (!mIsTicking) {
            mTimer.postTimerDelay();
        } else {
            mTimer.removeTimerCallbacks();
        }
        mIsTicking = !mIsTicking;
    }

    @Override
    public void onResetClicked() {
        mTimer.removeTimerCallbacks();
        mTimer.resetTimerProgress();

        mIsTicking = false;
    }

    @Override
    public void onDieClicked() {
        if (!mIsRolling) {
            mDie.postDieDelayed();
            mIsRolling = true;
        }
    }

    @Override
    public void setTimerText(@NonNull String text) {
        mTimerText = text;
        ((ScatTimer.TimerView) mPagerAdapter.getItem(0)).setTimerText(text);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(1)).setTimerText(text);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(2)).setTimerText(text);
    }

    @Override
    public void setIsTicking(int ticking) {
        mTickingState = ticking;
        ((ScatTimer.TimerView) mPagerAdapter.getItem(0)).setIsTicking(ticking);
        ((ScatTimer.TimerView) mPagerAdapter.getItem(1)).setIsTicking(ticking);
        //((ScatTimer.TimerView) mPagerAdapter.getItem(2)).setIsTicking(ticking);
    }

    @Override
    public void setIsRolling(boolean rolling) {
        mIsRolling = rolling;
    }

    @Override
    public void setDieText(@NonNull String text) {
        Log.d("DIETEXT", text);
        mDieText = text;
        ((ScatDie.DieView) mPagerAdapter.getItem(0)).setDieText(text);
        ((ScatDie.DieView) mPagerAdapter.getItem(1)).setDieText(text);
        ((ScatDie.DieView) mPagerAdapter.getItem(2)).setDieText(text);
    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        private ScatView mScatView;
        private ListView mListView;
        private EditableListView mEditableListView;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

            mScatView = new ScatView();
            mListView = new ListView();
            mEditableListView = new EditableListView();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mScatView;
            }
            if (position == 1) {
                return mListView;
            }
            if (position == 2) {
                return mEditableListView;
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        public void setScatListener(ScatView.ScatViewListener listener) {
            mScatView.setListener(listener);
        }

        public void setListListener(ListView.ListViewListener listener) {
            mListView.setListener(listener);
        }

        public void setEditableListListener(EditableListView.EditableListViewListener listener) {
            mEditableListView.setListener(listener);
        }
    }
}