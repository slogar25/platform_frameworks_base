/*
 * Copyright (C) 2010 The Android Open Source Project
 * This code has been modified. Portions copyright (C) 2012, ParanoidAndroid Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.statusbar.view;

import android.app.Notification;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;


import android.animation.TimeInterpolator;

import android.database.ContentObserver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;





import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;




import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.graphics.LightingColorFilter;
import android.graphics.Typeface;

import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.ColorUtils;



import android.view.animation.AccelerateDecelerateInterpolator;

import android.view.animation.AccelerateInterpolator;

import android.view.animation.DecelerateInterpolator;
import android.view.Gravity;



import android.view.LayoutInflater;


import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.ColorUtils;
import android.util.ExtendedPropertiesUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;



import android.widget.ImageView;
import android.widget.ScrollView;





import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.statusbar.StatusBarIcon;





import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.statusbar.StatusBarIcon;


import com.android.systemui.R;

import com.android.systemui.statusbar.PieControlPanel;






import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.statusbar.StatusBarIcon;

import com.android.systemui.R;

import com.android.systemui.statusbar.NotificationData;
import com.android.systemui.statusbar.PieControl;
import com.android.systemui.statusbar.PieControlPanel;
import com.android.systemui.statusbar.StatusBarIconView;


import com.android.systemui.statusbar.phone.QuickSettingsContainerView;


import com.android.systemui.statusbar.phone.QuickSettingsContainerView;

import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.NotificationRowLayout;
import com.android.systemui.statusbar.policy.PiePolicy;







import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.statusbar.StatusBarIcon;





import com.android.internal.statusbar.StatusBarNotification;
import com.android.internal.statusbar.StatusBarIcon;






import java.util.ArrayList;
import java.util.List;

public class PieMenu extends FrameLayout {




    private static final int MAX_LEVELS = 5;

    private static final long ANIMATION = 0;



    private static final int BACKGROUND_COLOR = 0xCC;
    private static final int ANIMATION_OUT = 0;



    public interface PieController {
        /**
         * called before menu opens to customize menu
         * returns if pie state has been changed
         */
        public boolean onOpen();

    private static final int COLOR_DEFAULT_BACKGROUND = 0xAAFF005E;
    private static final int COLOR_DEFAULT_SELECT = 0xAADBFF00;
    private static final int COLOR_DEFAULT_BUTTONS = 0xB2FFFFFF;
    private static final int COLOR_DEFAULT_STATUS = 0xFFFFFFFF;
    private static final int COLOR_DEFAULT_BATTERY_JUICE = 0x33b5e5;
    private static final int COLOR_DEFAULT_BATTERY_JUICE_LOW = 0xffbb33;
    private static final int COLOR_DEFAULT_BATTERY_JUICE_CRITICAL = 0xff4444;
    private static final int COLOR_DEFAULT_BATTERY_BACKGROUND = 0xFFFFFF;





    private static final int NOTIFICATIONS_PANEL = 1;
    private static final int QUICK_SETTINGS_PANEL = 2;

    private static final int SPEED_QUICK = 400;
    private static final int SPEED_DEFAULT = 1000;
    private static final int SPEED_SLOW = 2000;


    private static final int NOTIFICATIONS_PANEL = 0;
    private static final int QUICK_SETTINGS_PANEL = 1;



    // A view like object that lives off of the pie menu
    public interface PieView {
        public interface OnLayoutListener {
            public void onLayout(int ax, int ay, boolean left);
        }
        public void setLayoutListener(OnLayoutListener l);
        public void layout(int anchorX, int anchorY, boolean onleft, float angle,
                int parentHeight);
        public void draw(Canvas c);
        public boolean onTouchEvent(MotionEvent evt);
    }



    // Linear
    private static int ANIMATOR_DEC_SPEED15 = 1;
    private static int ANIMATOR_ACC_SPEED15 = 2;

    // Cascade
    private static int ANIMATOR_ACC_INC_1 = ANIMATOR_ACC_SPEED15 + 1;
    private static int ANIMATOR_ACC_INC_15 = ANIMATOR_ACC_INC_1 + 15;

    // Special purpose
    private static int ANIMATOR_BATTERY_METER = ANIMATOR_ACC_INC_15 + 1;
    private static int ANIMATOR_SNAP_GROW = ANIMATOR_ACC_INC_15 + 2;
    private static int ANIMATOR_END = ANIMATOR_SNAP_GROW;

    private static final int COLOR_OUTLINES_MASK = 0x22000000;
    private static final int COLOR_ALPHA_MASK = 0xaa000000;
    private static final int COLOR_OPAQUE_MASK = 0xff000000;
    private static final int COLOR_SNAP_BACKGROUND = 0xffffffff;
    private static final int COLOR_PIE_BACKGROUND = 0xaa000000;
    private static final int COLOR_PIE_BUTTON = 0xb2ffffff;
    private static final int COLOR_PIE_SELECT = 0xaaffffff;
    private static final int COLOR_PIE_OUTLINES = 0x55ffffff;
    private static final int COLOR_CHEVRON_LEFT = 0x0999cc;
    private static final int COLOR_CHEVRON_RIGHT = 0x53d5e5;
    private static final int COLOR_BATTERY_JUICE = 0x33b5e5;
    private static final int COLOR_BATTERY_JUICE_LOW = 0xffbb33;
    private static final int COLOR_BATTERY_JUICE_CRITICAL = 0xff4444;
    private static final int COLOR_BATTERY_BACKGROUND = 0xffffff;
    private static final int COLOR_STATUS = 0xffffff;
    private static final int BASE_SPEED = 1000;





    private static final int EMPTY_ANGLE_BASE = 10;

    private static final int EMPTY_ANGLE_BASE = 12;


    // private static final int EMPTY_ANGLE_BASE = 12;


    private static final int EMPTY_ANGLE_BASE = 10;




    private static final int EMPTY_ANGLE_BASE = 0;

    private static final int CHEVRON_FRAGMENTS = 16;
    private static final float SIZE_BASE = 1.0f;





    // System
    private Context mContext;
    private Resources mResources;
    private PiePolicy mPolicy;
    private Vibrator mVibrator;



    // Geometry
    private Point mCenter = new Point(0, 0);
    private int mRadius;
    private int mRadiusInc;
    private int mSlop;
    private int mTouchOffset;

    private List<PieItem> mItems;
    private PieView mPieView;


    // sub menus
    private PieItem mOpenItem;

    private Drawable mBackground;
    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private Paint mSubPaint;
    private Paint mBatteryJuice;
    private Paint mBatteryBackground;
    private Paint mGlowPaint;



    // touch handling

    // Pie handlers


    // Pie handlers

    private PieItem mCurrentItem;
    private List<PieItem> mItems;
    private PieControlPanel mPanel;
    private PieStatusPanel mStatusPanel;

    private int mOverallSpeed = BASE_SPEED;
    private int mPanelDegree;
    private int mPanelOrientation;
    private int mInnerPieRadius;
    private int mOuterPieRadius;
    private int mPieAngle;
    private int mPieGap;
    private int mInnerChevronRadius;
    private int mOuterChevronRadius;
    private int mInnerChevronRightRadius;
    private int mOuterChevronRightRadius;
    private int mInnerBatteryRadius;
    private int mOuterBatteryRadius;
    private int mStatusRadius;
    private int mNotificationsRadius;
    private int mEmptyAngle;



    // Colors
    private ColorUtils.ColorSettingInfo mLastBackgroundColor;
    private ColorUtils.ColorSettingInfo mLastGlowColor;



    private boolean mGlowColorHelper;

    private int mBackgroundOpacity;

    private float mTextOffset;
    private int mTextAlpha;
    private float mTextLen;
    private Path mStatusPath;
    private String mStatusText;

    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private Paint mBatteryJuice;
    private Paint mBatteryBackground;

    private Paint mStatusPaint;
    private Paint mChevronBackground1;
    private Paint mChevronBackground2;

    // Animations
    private ValueAnimator mIntoAnimation;
    private ValueAnimator mOutroAnimation;


    private Vibrator mVibrator;



    private int mBackgroundOpacity = 0;
    private float mTextOffset = 0;
    private int mTextAlpha = 0;
    private float mCharOffset[];
    private int mGlowOffsetLeft = 90;
    private int mGlowOffsetRight = 90;
    private int mBatteryBackgroundAlpha = 0;
    private int mBatteryJuiceAlpha = 0;
    private float mBatteryMeter = 0;
    private int mOverallSpeed = SPEED_DEFAULT;
    private float mDecelerateFraction = 0;



    private Point mCenter = new Point(0, 0);
    private float mCenterDistance = 0;

    private Path mStatusPath = new Path();
    private Path[] mChevronPathLeft  = new Path[CHEVRON_FRAGMENTS+1];
    private Path mChevronPathRight;
    private Path mBatteryPathBackground;
    private Path mBatteryPathJuice;

    private Paint mPieBackground = new Paint(COLOR_PIE_BACKGROUND);
    private Paint mPieSelected = new Paint(COLOR_PIE_SELECT);
    private Paint mPieOutlines = new Paint(COLOR_PIE_OUTLINES);
    private Paint mChevronBackgroundLeft = new Paint(COLOR_CHEVRON_LEFT);
    private Paint mChevronBackgroundRight = new Paint(COLOR_CHEVRON_RIGHT);
    private Paint mBatteryJuice = new Paint(COLOR_BATTERY_JUICE);
    private Paint mBatteryBackground = new Paint(COLOR_BATTERY_BACKGROUND);
    private Paint mSnapBackground = new Paint(COLOR_SNAP_BACKGROUND);

    private Paint mClockPaint;
    private Paint mAmPmPaint;
    private Paint mStatusPaint;
    private Paint mNotificationPaint;

    private String mClockText;
    private String mClockTextAmPm;
    private float mClockTextAmPmSize;
    private float mClockTextTotalOffset = 0;
    private float[] mClockTextOffsets = new float[20];
    private float mClockTextRotation;
    private float mClockOffset;
    private float mAmPmOffset;
    private float mStatusOffset;

    private int mNotificationCount;
    private float mNotificationsRowSize;
    private int mNotificationIconSize;
    private int mNotificationTextSize;
    private String[] mNotificationText;
    private Bitmap[] mNotificationIcon;
    private Path[] mNotificationPath;

    private float mStartBattery;
    private float mEndBattery;
    private int mBatteryLevel;





    class SnapPoint {

    private class SnapPoint {


    private class SnapPoint {

        public SnapPoint(int snapX, int snapY, int snapRadius, int snapAlpha, int snapGravity) {
            x = snapX;
            y = snapY;
            radius = snapRadius;
            alpha = snapAlpha;
            gravity = snapGravity;
            active = false;
        }

        public int x;
        public int y;
        public int radius;
        public int alpha;
        public int gravity;
        public boolean active;
    }

    private SnapPoint[] mSnapPoint = new SnapPoint[3];
    int mSnapRadius;
    int mSnapThickness;





    // Flags
    private int mStatusMode;
    private float mPieSize = SIZE_BASE;
    private boolean mOpen;


    private boolean mStatusAnimate;
    private boolean mGlowColorHelper;
    private int mStatusMode = 2;

    // Layout and UI



    private ViewManager mPanelParent;

    private ViewGroup mPanelParent;


    private ViewGroup[] mPanelParents;

    private ScrollView mScrollView;
    private View mContainer;
    private View mContentFrame;
    private QuickSettingsContainerView mQS;
    private NotificationRowLayout mNotificationPanel;
    private int mCurrentViewState = -1;
    private int mFlipViewState = -1;


    /**
     * @param context
     * @param attrs
     * @param defStyle
     */

    private Path mStatusPath;
    private Path mChevronPathLeft;
    private Path mChevronPathRight;
    private float mTextLen = 0;
    private String mStatusText;




    //private float mScrollY;




    public PieMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);


    private boolean mNavbarZero;
    private boolean mUseMenuAlways;
    private boolean mUseSearch;
    private boolean mHapticFeedback;


    // Animations
    private int mGlowOffsetLeft = 150;
    private int mGlowOffsetRight = 150;

    private class CustomValueAnimator {

        public CustomValueAnimator(int animateIndex) {
            index = animateIndex;
            manual = false;
            animateIn = true;
            animator = ValueAnimator.ofInt(0, 1);
            animator.addUpdateListener(new CustomAnimatorUpdateListener(index));
            fraction = 0;
        }

        public void start() {
            if (!manual) {
                animator.setDuration(duration);
                animator.start();
            }
        }

        public void reverse(int milliSeconds) {
            if (!manual) {
                animator.setDuration(milliSeconds);
                animator.reverse();
            }
        }

        public void cancel() {
            animator.cancel();
            fraction = 0;
        }

        public int index;
        public int duration;
        public boolean manual;
        public boolean animateIn;
        public float fraction;
        public ValueAnimator animator;
    }

    private CustomValueAnimator[] mAnimators = new CustomValueAnimator[ANIMATOR_END + 1];

    private float mX = 0;
    private float mY = 0;

    private void getDimensions() {
        mPanelDegree = mPanel.getDegree();
        mPanelOrientation = mPanel.getOrientation();

        // Fetch modes


        boolean expanded = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.EXPANDED_DESKTOP_STATE, 0) == 1;
        mUseMenuAlways = Settings.System.getInt(mContext.getContentResolver(), Settings.System.PIE_MENU, 1) == 1;
        mUseSearch = Settings.System.getInt(mContext.getContentResolver(), Settings.System.PIE_SEARCH, 1) == 1;
        mNavbarZero = Integer.parseInt(ExtendedPropertiesUtils.getProperty(
                "com.android.systemui.navbar.dpi", "100")) == 0 && !expanded;

        mStatusMode = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_MODE, 2);
        mPieSize = Settings.System.getFloat(mContext.getContentResolver(),
                Settings.System.PIE_SIZE, 1.0f);
        mPieAngle = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_ANGLE, 12);
        mPieGap = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_GAP, 2);
        mHapticFeedback = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;

        // Snap
        mSnapRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_snap_radius) * mPieSize);
        mSnapThickness = (int)(mResources.getDimensionPixelSize(R.dimen.pie_snap_thickness) * mPieSize);

        Point outSize = new Point(0,0);
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(outSize);
        int mWidth = outSize.x;
        int mHeight = outSize.y;

        int snapIndex = 0;
        if (mPanelOrientation != Gravity.LEFT)
            mSnapPoint[snapIndex++] = new SnapPoint(0 + mSnapThickness / 2, mHeight / 2, mSnapRadius, 0x22, Gravity.LEFT);
        if (mPanelOrientation != Gravity.TOP)
            mSnapPoint[snapIndex++] = new SnapPoint(mWidth / 2, mSnapThickness / 2, mSnapRadius, 0x22, Gravity.TOP);
        if (mPanelOrientation != Gravity.RIGHT)
            mSnapPoint[snapIndex++] = new SnapPoint(mWidth - mSnapThickness / 2, mHeight / 2, mSnapRadius, 0x22, Gravity.RIGHT);
        if (mPanelOrientation != Gravity.BOTTOM)
            mSnapPoint[snapIndex++] = new SnapPoint(mWidth / 2, mHeight - mSnapThickness / 2, mSnapRadius, 0x22, Gravity.BOTTOM);

        // Create Pie
        mEmptyAngle = (int)(mPieAngle * mPieSize); 
        mInnerPieRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_radius_start) * mPieSize);
        mOuterPieRadius = (int)(mInnerPieRadius + mResources.getDimensionPixelSize(R.dimen.pie_radius_increment) * mPieSize);

        // Calculate chevrons: 0 - 82 & -4 - 90
        mInnerChevronRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_chevron_start) * mPieSize);
        mOuterChevronRadius = (int)(mInnerChevronRadius + mResources.getDimensionPixelSize(R.dimen.pie_chevron_increment) * mPieSize);
        mInnerChevronRightRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_chevron_start_right) * mPieSize);
        mOuterChevronRightRadius = (int)(mInnerChevronRightRadius + mResources.getDimensionPixelSize(R.dimen.pie_chevron_increment_right) * mPieSize);

        // Create slices
        float fragmentSize = 90 / CHEVRON_FRAGMENTS;
        for (int i=0; i < CHEVRON_FRAGMENTS + 1; i++) {
            mChevronPathLeft[i] = makeSlice(mPanelDegree + (i * fragmentSize), mPanelDegree + (i * fragmentSize) + fragmentSize / 2,
                    mInnerChevronRadius, mOuterChevronRadius, mCenter);
        }

        mChevronPathRight = makeSlice(mPanelDegree + (mPanelOrientation != Gravity.TOP ? -5 : 3), mPanelDegree + 90, mInnerChevronRightRadius,
                mOuterChevronRightRadius, mCenter);

        // Calculate text circle
        mStatusRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_status_start) * mPieSize);
        mStatusPath.reset();
        mStatusPath.addCircle(mCenter.x, mCenter.y, mStatusRadius, Path.Direction.CW);

        mClockPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.pie_clock_size) * mPieSize);
        mClockOffset = mResources.getDimensionPixelSize(R.dimen.pie_clock_offset) * mPieSize;
        mAmPmPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.pie_ampm_size) * mPieSize);
        mAmPmOffset = mResources.getDimensionPixelSize(R.dimen.pie_ampm_offset) * mPieSize;

        mStatusPaint.setTextSize((int)(mResources.getDimensionPixelSize(R.dimen.pie_status_size) * mPieSize));
        mStatusOffset = mResources.getDimensionPixelSize(R.dimen.pie_status_offset) * mPieSize;
        mNotificationTextSize = (int)(mResources.getDimensionPixelSize(R.dimen.pie_notification_size) * mPieSize);
        mNotificationPaint.setTextSize(mNotificationTextSize);

        // Battery
        mInnerBatteryRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_battery_start) * mPieSize);
        mOuterBatteryRadius = (int)(mInnerBatteryRadius + mResources.getDimensionPixelSize(R.dimen.pie_battery_increment) * mPieSize);

        mBatteryBackground.setColor(COLOR_BATTERY_BACKGROUND);
        mBatteryLevel = mPolicy.getBatteryLevel();
        if(mBatteryLevel <= PiePolicy.LOW_BATTERY_LEVEL
                && mBatteryLevel > PiePolicy.CRITICAL_BATTERY_LEVEL) {
            mBatteryJuice.setColor(COLOR_BATTERY_JUICE_LOW);
        } else if(mBatteryLevel <= PiePolicy.CRITICAL_BATTERY_LEVEL) {
            mBatteryJuice.setColor(COLOR_BATTERY_JUICE_CRITICAL);
        } else {
            mBatteryJuice.setColor(COLOR_BATTERY_JUICE);
        }

        mStartBattery = mPanel.getDegree() + mEmptyAngle + mPieGap;
        mEndBattery = mPanel.getDegree() + (mPieGap <= 2 ? 88 : 90 - mPieGap);
        mBatteryPathBackground = makeSlice(mStartBattery, mEndBattery, mInnerBatteryRadius, mOuterBatteryRadius, mCenter);
        mBatteryPathJuice = makeSlice(mStartBattery, mStartBattery, mInnerBatteryRadius, mOuterBatteryRadius, mCenter);

        // Colors


        boolean pac = mPerAppColor && ColorUtils.getPerAppColorState(mContext);




        ColorUtils.ColorSettingInfo buttonColorInfo = ColorUtils.getColorSettingInfo(mContext,
                Settings.System.NAV_BUTTON_COLOR);

        mNotificationPaint.setColor(COLOR_STATUS);
        mSnapBackground.setColor(COLOR_SNAP_BACKGROUND);
        mStatusPaint.setColor(COLOR_STATUS);



        if (pac) {

        if (ColorUtils.getPerAppColorState(mContext)) {


        if (ColorUtils.getPerAppColorState(mContext)) {

            ColorUtils.ColorSettingInfo colorInfo;
            colorInfo = ColorUtils.getColorSettingInfo(mContext, Settings.System.NAV_BAR_COLOR);
            mPieOutlines.setColor(ColorUtils.extractRGB(colorInfo.lastColor) | COLOR_OUTLINES_MASK);           

            colorInfo = ColorUtils.getColorSettingInfo(mContext, Settings.System.NAV_GLOW_COLOR);
            mPieSelected.setColor(ColorUtils.extractRGB(colorInfo.lastColor) | COLOR_ALPHA_MASK);

            colorInfo = ColorUtils.getColorSettingInfo(mContext, Settings.System.STATUS_ICON_COLOR);
            mClockPaint.setColor(colorInfo.lastColor);
            mAmPmPaint.setColor(colorInfo.lastColor);
            mClockPaint.setColor(colorInfo.lastColor);
            mPieBackground.setColor(ColorUtils.extractRGB(colorInfo.lastColor) | COLOR_ALPHA_MASK);

            mChevronBackgroundLeft.setColor(ColorUtils.extractRGB(buttonColorInfo.lastColor) | COLOR_OPAQUE_MASK);
            mChevronBackgroundRight.setColor(ColorUtils.extractRGB(buttonColorInfo.lastColor) | COLOR_OPAQUE_MASK);            
            mBatteryJuice.setColorFilter(buttonColorInfo.isLastColorNull ? null :
                    new PorterDuffColorFilter(ColorUtils.extractRGB(buttonColorInfo.lastColor) | COLOR_OPAQUE_MASK, Mode.SRC_ATOP));

            buttonColorInfo = ColorUtils.getColorSettingInfo(mContext, Settings.System.NAV_BUTTON_COLOR);
            for (PieItem item : mItems) {
                item.setColor(buttonColorInfo.isLastColorNull ? COLOR_PIE_BUTTON : buttonColorInfo.lastColor);
            }
        } else {
            mPieBackground.setColor(COLOR_PIE_BACKGROUND);
            mPieSelected.setColor(COLOR_PIE_SELECT);
            mPieOutlines.setColor(COLOR_PIE_OUTLINES);
            mClockPaint.setColor(COLOR_STATUS);
            mAmPmPaint.setColor(COLOR_STATUS);
            mChevronBackgroundLeft.setColor(COLOR_CHEVRON_LEFT);
            mChevronBackgroundRight.setColor(COLOR_CHEVRON_RIGHT);
            mBatteryJuice.setColorFilter(null);
        }



        buttonColorInfo = ColorUtils.getColorSettingInfo(mContext, Settings.System.NAV_BUTTON_COLOR);

        for (PieItem item : mItems) {
            item.setColor(buttonColorInfo.lastColor, buttonColorInfo.isLastColorNull ? false : pac);

        if(!buttonColorInfo.isLastColorNull){
            for (PieItem item : mItems) {
                item.setColor(buttonColorInfo.lastColor);
            }

        }





        // Notifications
        mNotificationCount = 0;
        mNotificationsRadius = (int)(mResources.getDimensionPixelSize(R.dimen.pie_notifications_start) * mPieSize);
        mNotificationIconSize = (int)(mResources.getDimensionPixelSize(R.dimen.pie_notification_icon_size) * mPieSize);
        mNotificationsRowSize = mResources.getDimensionPixelSize(R.dimen.pie_notification_row_size) * mPieSize;

        if (mPanel.getBar() != null) {
            getNotifications();
        }

        // Measure clock
        measureClock(mPolicy.getSimpleTime());

        // Determine animationspeed
        mOverallSpeed = BASE_SPEED / 4;
        int mInitialSpeed = BASE_SPEED * (mStatusMode == -1 ? 0 : mStatusMode) / 2;

        // Create animators
        for (int i = 0; i < mAnimators.length; i++) {
            mAnimators[i] = new CustomValueAnimator(i);
        }

        // Linear animators
        mAnimators[ANIMATOR_DEC_SPEED15].duration = (int)(mOverallSpeed * 1.5);
        mAnimators[ANIMATOR_DEC_SPEED15].animator.setInterpolator(new DecelerateInterpolator());
        mAnimators[ANIMATOR_DEC_SPEED15].animator.setStartDelay((int)(mInitialSpeed * 1.5));

        mAnimators[ANIMATOR_ACC_SPEED15].duration = (int)(mOverallSpeed * 1.5);
        mAnimators[ANIMATOR_ACC_SPEED15].animator.setInterpolator(new AccelerateInterpolator());
        mAnimators[ANIMATOR_ACC_SPEED15].animator.setStartDelay((int)(mInitialSpeed * 1.5));

        // Cascade accelerators
        int count = 0;
        for(int i = ANIMATOR_ACC_INC_1; i < ANIMATOR_ACC_INC_15 + 1; i++) {
            mAnimators[i].duration = 150;
            mAnimators[i].animator.setInterpolator(new DecelerateInterpolator());
            mAnimators[i].animator.setStartDelay((int)(mInitialSpeed * 1.5f + (++count * 75) ));
        }

        // Special purpose




        mAnimators[ANIMATOR_BATTERY_METER].setDuration((int)(mOverallSpeed * 1.5));
        mAnimators[ANIMATOR_BATTERY_METER].setInterpolator(new DecelerateInterpolator());






        mAnimators[ANIMATOR_SNAP_WOBBLE].setDuration(400);
        mAnimators[ANIMATOR_SNAP_WOBBLE].setInterpolator(new DecelerateInterpolator());
        mAnimators[ANIMATOR_SNAP_WOBBLE].setRepeatMode(ValueAnimator.REVERSE);
        mAnimators[ANIMATOR_SNAP_WOBBLE].setRepeatCount(ValueAnimator.INFINITE);





        mAnimators[ANIMATOR_BATTERY_METER].animator.setDuration((int)(mOverallSpeed * 1.5));

        mAnimators[ANIMATOR_BATTERY_METER].duration = (int)(mOverallSpeed * 1.5);


        mAnimators[ANIMATOR_BATTERY_METER].duration = (int)(mOverallSpeed * 3);


        mAnimators[ANIMATOR_BATTERY_METER].duration = (int)(mOverallSpeed * 1.5);

        mAnimators[ANIMATOR_BATTERY_METER].animator.setInterpolator(new DecelerateInterpolator());
        mAnimators[ANIMATOR_BATTERY_METER].animator.setStartDelay((int)(mInitialSpeed * 1.5));


        mAnimators[ANIMATOR_SNAP_WOBBLE].manual = true;
        mAnimators[ANIMATOR_SNAP_WOBBLE].animator.setDuration(400);
        mAnimators[ANIMATOR_SNAP_WOBBLE].animator.setInterpolator(new DecelerateInterpolator());
        mAnimators[ANIMATOR_SNAP_WOBBLE].animator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimators[ANIMATOR_SNAP_WOBBLE].animator.setRepeatCount(ValueAnimator.INFINITE);





        mAnimators[ANIMATOR_SNAP_GROW].manual = true;
        mAnimators[ANIMATOR_SNAP_GROW].animator.setDuration(1000);
        mAnimators[ANIMATOR_SNAP_GROW].animator.setInterpolator(new AccelerateInterpolator());
        mAnimators[ANIMATOR_SNAP_GROW].animator.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationEnd(Animator animation) {
                if (mAnimators[ANIMATOR_SNAP_GROW].fraction == 1) {
                    for (int i = 0; i < 3; i++) {
                        SnapPoint snap = mSnapPoint[i];
                        if (snap.active) {
                            if(mHapticFeedback) mVibrator.vibrate(2);
                            mStatusPanel.hidePanels(true);
                            deselect();
                            animateOut();
                            mPanel.reorient(snap.gravity, true);
                        }
                    }
                }
            }});

    }

    private void measureClock(String text) {
        mClockText = text;

        mClockTextAmPm = mPolicy.getAmPm();
        mClockTextAmPmSize = mAmPmPaint.measureText(mClockTextAmPm);
        mClockTextTotalOffset = 0;

        for( int i = 0; i < mClockText.length(); i++ ) {
            char character = mClockText.charAt(i);
            float measure = mClockPaint.measureText("" + character); 
            mClockTextOffsets[i] = measure * (character == '1' || character == ':' ? 0.5f : 0.8f);
            mClockTextTotalOffset += measure * (character == '1' || character == ':' ? 0.6f : 0.9f);
        }

        mClockTextRotation = mPanel.getDegree() + (180 - (mClockTextTotalOffset * 360 /
                (2f * (mStatusRadius+Math.abs(mClockOffset)) * (float)Math.PI))) - 2;
    }

    private void getNotifications() {
        NotificationData notifData = mPanel.getBar().getNotificationData();
        if (notifData != null) {

            mNotificationText = new String[notifData.size()];
            mNotificationIcon = new Bitmap[notifData.size()];
            mNotificationPath = new Path[notifData.size()];

            for (int i = 0; i < notifData.size(); i++ ) {
                NotificationData.Entry entry = notifData.get(i);
                StatusBarNotification statusNotif = entry.notification;
                if (statusNotif == null) continue;
                Notification notif = statusNotif.notification;
                if (notif == null) continue;
                CharSequence tickerText = notif.tickerText;
                if (tickerText == null) continue;

                if (entry.icon != null) {
                    StatusBarIconView iconView = entry.icon;
                    StatusBarIcon icon = iconView.getStatusBarIcon();
                    Drawable drawable = entry.icon.getIcon(mContext, icon);
                    if (!(drawable instanceof BitmapDrawable)) continue;
                    
                    mNotificationIcon[mNotificationCount] = ((BitmapDrawable)drawable).getBitmap();

                    String text = tickerText.toString();
                    if (text.length() > 100) text = text.substring(0, 100) + "..";
                    mNotificationText[mNotificationCount] = text;

                    Path notifictionPath = new Path();
                    notifictionPath.addCircle(mCenter.x, mCenter.y, mNotificationsRadius +
                            (mNotificationsRowSize * mNotificationCount) + (mNotificationsRowSize-mNotificationTextSize),
                            Path.Direction.CW);
                    mNotificationPath[mNotificationCount] = notifictionPath;

                    mNotificationCount++;
                }
            }
        }
    }



    private final class ColorObserver extends ContentObserver {
        ColorObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.NAV_BAR_COLOR), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.NAV_GLOW_COLOR), false, this);
            setColors();
        }

        @Override
        public void onChange(boolean selfChange) {
            setColors();
        }
    }

    private void init(Context ctx) {
        mContext = ctx;


        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        mPolicy = new PiePolicy(mContext);


        mItems = new ArrayList<PieItem>();
        Resources res = ctx.getResources();
        mRadius = (int) res.getDimension(R.dimen.pie_radius_start);
        mRadiusInc = (int) res.getDimension(R.dimen.pie_radius_increment);
        mSlop = (int) res.getDimension(R.dimen.pie_slop);
        mTouchOffset = (int) res.getDimension(R.dimen.pie_touch_offset);
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);

        mCenter = new Point(0, 0);
        mBackground = new ColorDrawable(0x00000000);
        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mSelectedPaint = new Paint();
        mSelectedPaint.setAntiAlias(true);


        mSelectedPaint.setColor(0xAA33b5e5);

        mGlowPaint = new Paint(0xAA33b5e5);
        mGlowPaint.setColorFilter(new LightingColorFilter(0xAA33b5e5, 1));


        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setColor(COLOR_DEFAULT_BACKGROUND);

        mSelectedPaint = new Paint();
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setColor(COLOR_DEFAULT_SELECT);


        mBatteryJuice = new Paint();



    public PieMenu(Context context, PieControlPanel panel) {
        super(context);

        mContext = context;
        mResources = mContext.getResources();
        mPanel = panel;

        setWillNotDraw(false);
        setDrawingCacheEnabled(false);

        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mPolicy = new PiePolicy(mContext);

        // Initialize classes
        mItems = new ArrayList<PieItem>();
        mPieBackground.setAntiAlias(true);
        mPieSelected.setAntiAlias(true);
        mPieOutlines.setAntiAlias(true);
        mPieOutlines.setStyle(Style.STROKE);
        mPieOutlines.setStrokeWidth(mResources.getDimensionPixelSize(R.dimen.pie_outline));
        mChevronBackgroundLeft.setAntiAlias(true);
        mChevronBackgroundRight.setAntiAlias(true);




        mBatteryJuice.setAntiAlias(true);
        mBatteryBackground.setAntiAlias(true);
        mSnapBackground.setAntiAlias(true);




        mBatteryBackground.setColor(0xFFFFFF);
        

        mSubPaint = new Paint();
        mSubPaint.setAntiAlias(true);
        mSubPaint.setColor(0xFF000000);
        mLastBackgroundColor = ColorUtils.getColorSettingInfo(mContext,
                Settings.System.NAV_BAR_COLOR);
        mLastGlowColor = ColorUtils.getColorSettingInfo(mContext,
                Settings.System.NAV_GLOW_COLOR);


        ColorObserver observer = new ColorObserver(new Handler());
        observer.observe();

        mBatteryBackground.setColor(COLOR_DEFAULT_BATTERY_BACKGROUND);





        Typeface robotoThin = Typeface.create("sans-serif-light", Typeface.NORMAL);



        mClockPaint = new Paint();
        mClockPaint.setAntiAlias(true);     
        mClockPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));

        mAmPmPaint = new Paint();
        mAmPmPaint.setAntiAlias(true);
        mAmPmPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));

        mStatusPaint = new Paint();
        mStatusPaint.setAntiAlias(true);
        mStatusPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));

        mNotificationPaint = new Paint();
        mNotificationPaint.setAntiAlias(true);
        mNotificationPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));




        // Circle status text
        mCharOffset = new float[25];
        for (int i = 0; i < mCharOffset.length; i++) {
            mCharOffset[i] = 1000;
        }

        mStatusText = mPolicy.getSimpleTime();
        mTextLen = mStatusPaint.measureText(mStatusText, 0, mStatusText.length());

        // Clock observer


        // Clock observer

        mPolicy.setOnClockChangedListener(new PiePolicy.OnClockChangedListener() {
            public void onChange(String s) {
                measureClock(s);
            }
        });



        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        mContainer = inflater.inflate(R.layout.pie_notification_panel, null);

        mContentFrame = (View) mContainer.findViewById(R.id.content_frame);
        mScrollView = (ScrollView) mContainer.findViewById(R.id.notification_scroll);
        mScrollView.setOnTouchListener(new OnTouchListener(){


            final int SCROLLING_DISTANCE_TRIGGER = 100;
            float scrollX;
            float scrollY;
            boolean hasScrolled;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scrollX = event.getX();
                        scrollY = event.getY();
                        hasScrolled = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float distanceY = Math.abs(event.getY() - scrollY);
                        float distanceX = Math.abs(event.getX() - scrollX);
                        if(distanceY > SCROLLING_DISTANCE_TRIGGER ||
                            distanceX > SCROLLING_DISTANCE_TRIGGER) {
                            hasScrolled = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(!hasScrolled) {
                            hidePanels(true);
                        }
                        break;
                }
                return false;
            }                               
        });


            @Override
            public boolean onTouch(View v, MotionEvent event) {         
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    hideNotificationsPanel();
                }
                return false;
            }});


        mContainer.setVisibility(View.GONE);
        mWindowManager.addView(mContainer, getFlipPanelLayoutParams());


        mLastBackgroundColor = new ColorUtils.ColorSettingInfo();
        mLastGlowColor = new ColorUtils.ColorSettingInfo();

        // Only watch for per app color changes when the setting is in check
        if (ColorUtils.getPerAppColorState(mContext)) {
            setBackgroundColor();
            setGlowColor();

            // Listen for nav bar color changes
            mContext.getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.NAV_BAR_COLOR), false, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        setBackgroundColor();
                    }});

            // Listen for button glow color changes
            mContext.getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.NAV_GLOW_COLOR), false, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        setGlowColor();
                    }});
        }


        // Get all dimensions
        getDimensions();


        // Get all dimensions
        getDimensions();

    }

    public void init() {
        mStatusPanel = new PieStatusPanel(mContext, mPanel);
        getNotifications();
    }

    public void onConfigurationChanged() {
        if (mStatusPanel != null) mStatusPanel.updatePanelConfiguration();
    }

    public PieStatusPanel getStatusPanel() {
        return mStatusPanel;
    }

    public void addItem(PieItem item) {
        mItems.add(item);
    }

    public void show(boolean show) {
        mOpen = show;
        if (mOpen) {

            // Get fresh dimensions
            getDimensions();

            // De-select all items
            mCurrentItem = null;
            for (PieItem item : mItems) {
                item.setSelected(false);
            }

            // Calculate pie's
            layoutPie();

            animateOpen();


        }
        invalidate();
    }


    private void animateOpen() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (PieItem item : mItems) {
                    item.setAnimationAngle((1 - animation.getAnimatedFraction()) * (- item.getStart()));
                }
                invalidate();
            }

        });
        anim.setDuration(2 * ANIMATION);
        anim.start();
    }

    public void setCenter(int x, int y) {
        if (y < mSlop) {
            mCenter.y = 0;
        } else {
            mCenter.y = getHeight();
        }
        mCenter.x = x;



    }

    private void setColors() {
        // Only watch for per app color changes when the setting is in check
        if (ColorUtils.getPerAppColorState(mContext)) {
            setBackgroundColor();
            setGlowColor();
        }

        mStatusPath = new Path();
        mStatusPath.addCircle(mCenter.x, mCenter.y, mRadius+mRadiusInc, Path.Direction.CW);


        // Calculate text circle
        mStatusPath = new Path();
        mStatusPath.addCircle(mCenter.x, mCenter.y, mRadius+mRadiusInc, Path.Direction.CW);

        // Calculate chevrons
        int in = (int)((mRadius + mRadiusInc) + mTouchOffset * 7.5f);
        int o = (int)(mRadius + mRadiusInc + mTouchOffset * 7.65);
        float s = mPanel.getDegree() + 0;
        float e = mPanel.getDegree() + 90 - 8;
        mChevronPathLeft = makeSlice(s, e, in, o, mCenter);
        s = mPanel.getDegree() -4;
        e = mPanel.getDegree() + 90;
        mChevronPathRight = makeSlice(s, e, in, o, mCenter);

    }

    private void setBackgroundColor() {
        ColorUtils.ColorSettingInfo colorInfo = ColorUtils.getColorSettingInfo(mContext,
                Settings.System.NAV_BAR_COLOR);

        int newColor = 0xFF000000;
        if (!colorInfo.lastColorString.equals(mLastBackgroundColor.lastColorString)) {
            if (!colorInfo.isLastColorNull) {
                newColor = colorInfo.lastColor;
            }
            mNormalPaint.setColor(newColor);

        if (!colorInfo.lastColorString.equals(mLastBackgroundColor.lastColorString) && mPerAppColor) {
            int colorRgb = ColorUtils.extractRGB(colorInfo.lastColor);
            mNormalPaint.setColor(colorRgb | 0xAA000000);

            mLastBackgroundColor = colorInfo;
        }
    }

    private void setGlowColor() {
        ColorUtils.ColorSettingInfo colorInfo = ColorUtils.getColorSettingInfo(mContext,
                Settings.System.NAV_GLOW_COLOR);

        int newColor = 0xE033B5E5;
        if (!colorInfo.lastColorString.equals(mLastGlowColor.lastColorString)) {
            if (!colorInfo.isLastColorNull) {
                newColor = colorInfo.lastColor;
            }
            mSelectedPaint.setColor(newColor);
            setDrawingAlpha(mSelectedPaint, 0.7f);

        if (!colorInfo.lastColorString.equals(mLastGlowColor.lastColorString) && mPerAppColor) {
            ColorUtils.ColorSettingInfo buttonColorInfo = ColorUtils.getColorSettingInfo(mContext,
                    Settings.System.NAV_BUTTON_COLOR);

            // This helps us to discern when glow has the same color as the button color,
            // in which case we have to counteract in order to prevent both from swallowing each other
            int glowRgb = ColorUtils.extractRGB(colorInfo.lastColor);
            int buttonRgb = ColorUtils.extractRGB(buttonColorInfo.lastColor);
            mGlowColorHelper = glowRgb == buttonRgb;
            mSelectedPaint.setColor(glowRgb | 0xAA000000);
            mStatusPaint.setColor(glowRgb);

            mLastGlowColor = colorInfo;
        }
    }

    public void setDrawingAlpha(Paint paint, float x) {
        paint.setAlpha((int) (x * 255));

        mStatusPath.reset();
        mStatusPath.addCircle(mCenter.x, mCenter.y, mStatusRadius, Path.Direction.CW);


    public void setCenter(int x, int y) {
        mCenter.y = y;
        mCenter.x = x;

        mStatusPath.reset();
        mStatusPath.addCircle(mCenter.x, mCenter.y, mStatusRadius, Path.Direction.CW);

    }

    private boolean canItemDisplay(PieItem item) {
        return !(item.getName().equals(PieControl.MENU_BUTTON) && !mPanel.currentAppUsesMenu() && !mUseMenuAlways) &&
                !(item.getName().equals(PieControl.SEARCH_BUTTON) && !mUseSearch);
    }

    private void layoutPie() {
        float emptyangle = mEmptyAngle * (float)Math.PI / 180;
        int inner = mInnerPieRadius;
        int outer = mOuterPieRadius;

        int itemCount = mItems.size();
        if (!mPanel.currentAppUsesMenu() && !mUseMenuAlways) itemCount--;
        if (!mUseSearch) itemCount--;

        int totalCount = 0;
        int lesserSweepCount = 0;
        for (PieItem item : mItems) {
            boolean canDisplay = canItemDisplay(item);
            if (canDisplay) {
                totalCount++;
                if (item.isLesser()) {
                    lesserSweepCount += 1;
                }
            }
        }

        float adjustedSweep = lesserSweepCount > 0 ? (((1-0.65f) * lesserSweepCount) / (itemCount-lesserSweepCount)) : 0;    
        float sweep = 0;
        float angle = 0;
        float total = 0;

        int count = 0;
        for (PieItem item : mItems) {
            if (!canItemDisplay(item)) continue;

            sweep = ((float) (Math.PI - 2 * emptyangle) / itemCount) * (item.isLesser() ? 0.65f : 1 + adjustedSweep);
            angle = (emptyangle + sweep / 2 - (float)Math.PI/2);
            item.setPath(makeSlice(getDegrees(0) - mPieGap, getDegrees(sweep) + mPieGap, outer, inner, mCenter,
                    (mPieGap > 0 ? mPieGap + 0.4f : 0), count != 0));
            View view = item.getView();

            if (view != null) {
                view.measure(view.getLayoutParams().width, view.getLayoutParams().height);
                int w = view.getMeasuredWidth();
                int h = view.getMeasuredHeight();
                int r = inner + (outer - inner) * 2 / 3;
                int x = (int) (r * Math.sin(total + angle));
                int y = (int) (r * Math.cos(total + angle));

                switch(mPanelOrientation) {
                    case Gravity.LEFT:
                        y = mCenter.y - (int) (r * Math.sin(total + angle)) - h / 2;
                        x = (int) (r * Math.cos(total + angle)) - w / 2;
                        break;
                    case Gravity.RIGHT:
                        y = mCenter.y - (int) (Math.PI/2-r * Math.sin(total + angle)) - h / 2;
                        x = mCenter.x - (int) (r * Math.cos(total + angle)) - w / 2;
                        break;
                    case Gravity.TOP:
                        y = y - h / 2;
                        x = mCenter.x - (int)(Math.PI/2-x) - w / 2;
                        break;
                    case Gravity.BOTTOM: 
                        y = mCenter.y - y - h / 2;
                        x = mCenter.x - x - w / 2;
                        break;
                }                
                view.layout(x, y, x + w, y + h);
            }                    
            float itemstart = total + angle - sweep / 2;
            item.setGeometry(itemstart, sweep, inner, outer);
            total += sweep;
            count++;
        }
    }

    // param angle from 0..PI to Android degrees (clockwise starting at 3
    private float getDegrees(double angle) {
        return (float) (270 - 180 * angle / Math.PI);
    }









    class customAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    private class CustomAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {


    private class CustomAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {


        private int mIndex = 0;
        public CustomAnimatorUpdateListener(int index) {

        private int mIndex;
        CustomAnimatorUpdateListener(int index) {

            mIndex = index;
        }

        @Override

        public void onAnimationUpdate(ValueAnimator animation) {



            mCharOffset[mIndex] = (float)((1 - animation.getAnimatedFraction()) * mOverallSpeed);
            invalidate();
        }
    }

    /* NEW ANIMATION SYSTEM HERE, USING BASIC ACCELERATORS AND DECCELERATORS + FRACTIONS, NO SPECIFIC VARIABLES
    class customAnimatorListenerAdapter implements ValueAnimator.AnimatorListenerAdapter {
        private int mIndex = 0;
        public customAnimatorListenerAdapter(int index) {
            mIndex = index;
        }

        public void onAnimationEnd(Animator a) {



            mAnimatedFraction[mIndex] = (float)animation.getAnimatedFraction();


        public void onAnimationUpdate(ValueAnimator animation) {            

            mAnimators[mIndex].fraction = animation.getAnimatedFraction();


            // Special purpose animators go here
            if (mIndex == ANIMATOR_BATTERY_METER) {
                mBatteryPathJuice = makeSlice(mStartBattery, mStartBattery + (float)animation.getAnimatedFraction() *
                        (mBatteryLevel * (mEndBattery-mStartBattery) / 100), mInnerBatteryRadius, mOuterBatteryRadius, mCenter);
            }




            invalidate();
        }
    }




    private static int ANIMATOR_DEC_SPEED30 = 0;
    private static int ANIMATOR_DEC_SPEED15 = 0;
    private static int ANIMATOR_ACC_SPEED10 = 0;

    private ValueAnimator mAnimators[25];

        for (int i = 0; mAnimators.length; i++) {
            ValueAnimator animator = ValueAnimator.ofInt(0, 1);
            animator.addUpdateListener(new customAnimatorUpdateListener(i));
            animator.addListener(new customAnimatorListenerAdapter(i));
            mAnimators[i] = animator;
        }
    */







    private void animateIn() {
        // Cancel & start all animations

    private void cancelAnimation() {

        for (int i = 0; i < mAnimators.length; i++) {
            mAnimators[i].cancel();
        }
    }

    private void animateIn() {
        // Cancel & start all animations
        cancelAnimation();
        invalidate();
        for (int i = 0; i < mAnimators.length; i++) {
            mAnimators[i].animateIn = true;
            mAnimators[i].start();
        }
    }

    public void animateOut() {
        mPanel.show(false);
        cancelAnimation();
    }



    float mCharOffset[];



    int mGlowOffset = 0;






    @Override
    protected void onDraw(Canvas canvas) {
        if (mOpen) {
            int state;


            if (mUseBackground) {
                int w = mBackground.getIntrinsicWidth();
                int h = mBackground.getIntrinsicHeight();
                int left = mCenter.x - w;
                int top = mCenter.y - h / 2;
                mBackground.setBounds(left, top, left + w, top + h);
                state = canvas.save();
                if (onTheTop()) {
                    canvas.scale(-1, 1);
                }
                mBackground.draw(canvas);
                canvas.restoreToCount(state);


            // Draw background
            if (mStatusMode != 0) {

                canvas.drawARGB(mBackgroundOpacity, 0, 0, 0);


                canvas.drawARGB((int)(mAnimatedFraction[ANIMATOR_DEC_SPEED15] * 0xcc), 0, 0, 0);


            // Draw background

            if (mStatusMode != 0 && !mNavbarZero) {

                canvas.drawARGB((int)(mAnimatedFraction[ANIMATOR_DEC_SPEED15] * 0xcc), 0, 0, 0);



            if (mStatusMode != -1 && !mNavbarZero) {

                canvas.drawARGB((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * 0xcc), 0, 0, 0);

            }

            // Snap points
            if (mCenterDistance > mOuterChevronRadius) {
                for (int i = 0; i < 3; i++) {
                    SnapPoint snap = mSnapPoint[i];
                    mSnapBackground.setAlpha((int)(snap.alpha + (snap.active ? mAnimators[ANIMATOR_SNAP_GROW].fraction * 80 : 0)));



                    canvas.drawCircle (snap.x, snap.y, (snap.active ? mAnimators[ANIMATOR_SNAP_GROW].fraction *
                            Math.max(getWidth(), getHeight()) * 1.5f : 0), mSnapBackground);




                    mSnapBackground.setAlpha((int)(snapTouch));
                    int len = (int)(snap.radius * 1.3f);
                    int thick = (int)(len * 0.2f);


                    int wobble = 0;
                    if (snap.active) {
                        wobble = (int)(mAnimators[ANIMATOR_SNAP_WOBBLE].fraction * mSnapRadius / 2);
                        wobble = mSnapRadius + wobble;



                        /*mAnimators[ANIMATOR_SNAP_WOBBLE].setRepeatCount(0);
                        mAnimators[ANIMATOR_SNAP_WOBBLE].cancel();
                        mAnimatedFraction[ANIMATOR_SNAP_WOBBLE] = 0;*/


                    }
                    canvas.drawCircle (snap.x, snap.y, snap.radius + wobble, mSnapBackground);
                }
            }


            // Draw base menu
            for (PieItem item : mItems) {
                drawItem(canvas, item);
            }







            /*if (last != null) {
                drawItem(canvas, last);
            }
            if (mPieView != null) {
                mPieView.draw(canvas);

            }

            }*/

            //STATUS BAR FLOATING TEXT
            /*
            float width = (float)getWidth();
			float height = (float)getHeight();
			float radius;

			Path path = new Path();
			path.addCircle(mCenter.x, mCenter.y, mRadius, Path.Direction.CW);
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(80);

            String text = "00:03 MON";
            float w = paint.measureText(text, 0, text.length());
            canvas.drawTextOnPath(text, path, mTextOffset, -40, paint);

            paint.setColor(Color.RED);
            canvas.drawTextOnPath(text, path, -w, -40, paint);

            paint.setColor(Color.GREEN);
            canvas.drawTextOnPath(text, path, w, -40, paint);*/
            





            mStatusPath = new Path();
            mStatusPath.addCircle(mCenter.x, mCenter.y, mRadius+mRadiusInc+mTouchOffset, Path.Direction.CW);

            mStatusPaint = new Paint();
            mStatusPaint.setColor(Color.WHITE);
            mStatusPaint.setStyle(Paint.Style.FILL);
            mStatusPaint.setTextSize(125);
            mStatusPaint.setAlpha(mTextAlpha);
            mStatusPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            mStatusPaint.setTextScaleX(1.2f);
            
            state = canvas.save();
            canvas.rotate(90 + (mCharOffset[1] / 2), mCenter.x, mCenter.y);
            int inner = mRadius + 2;
            int outer = mRadius + mRadiusInc - 2;
            Path mBatteryPath = makeSlice(mPanel.getDegree() + 13, mPanel.getDegree()
                    + 90 - 2, outer + mTouchOffset * 2, outer + mTouchOffset, mCenter);

            mBatteryBackground.setAlpha(mBatteryBackgroundAlpha);
            canvas.drawPath(mBatteryPath, mBatteryBackground);
            canvas.restoreToCount(state);

            state = canvas.save();
            canvas.rotate(90, mCenter.x, mCenter.y);
            Path mBatteryPath2 = makeSlice(mPanel.getDegree() + 13, mPanel.getDegree()
                    + mBatteryMeter - 2, outer + mTouchOffset * 2, outer + mTouchOffset, mCenter);
            mBatteryJuice.setAlpha(mBatteryJuiceAlpha);
            canvas.drawPath(mBatteryPath2, mBatteryJuice);
            canvas.restoreToCount(state);


            // Time falling into place
            state = canvas.save();
            float pos = mPanel.getDegree() + 125;
            canvas.rotate(pos, mCenter.x, mCenter.y);
            float lastPos = 0;
            for( int i = 0; i < mStatusText.length(); i++ ) {
                char character = mStatusText.charAt(i);
                canvas.drawTextOnPath("" + character, mStatusPath, lastPos, -mCharOffset[i] - 40, mStatusPaint);
                lastPos += mStatusPaint.measureText("" + character) * (character == '1' || character == ':' ? 0.5f : 0.8f);
            }
            mStatusPaint.setTextSize(35);
            String amPm = mPolicy.getAmPm();
            lastPos -= mStatusPaint.measureText(amPm);
            canvas.drawTextOnPath(amPm, mStatusPath, lastPos, -mCharOffset[mStatusText.length()-1] - 140, mStatusPaint);
            canvas.restoreToCount(state);

            // Device status information and date
            state = canvas.save();
            pos = mPanel.getDegree() + 180;
            canvas.rotate(pos, mCenter.x, mCenter.y);
            mStatusPaint.setTextSize(20);
            canvas.drawTextOnPath(mPolicy.getNetworkProvider(), mStatusPath, mCharOffset[4], -75, mStatusPaint);
            canvas.drawTextOnPath(mPolicy.getSimpleDate(), mStatusPath, mCharOffset[4], -50, mStatusPaint);
            canvas.drawTextOnPath(mPolicy.getBatteryLevelReadable(), mStatusPath, mCharOffset[4], -25, mStatusPaint);
            canvas.drawTextOnPath(mPolicy.getWifiSsid(), mStatusPath, mCharOffset[4], 0, mStatusPaint);
            canvas.restoreToCount(state);


            // floating text
            /*
            state = canvas.save();
            canvas.rotate(mPanel.getDegree(), mCenter.x, mCenter.y);
            mStatusPaint.setAlpha(mTextAlpha);
            canvas.drawTextOnPath(mStatusText, mStatusPath, 0, 0, mStatusPaint);
            canvas.restoreToCount(state);

            if (mStatusAnimate) {
                mTextOffset += .4f;
                invalidate();
            }*/



            // Draw shade rings
            Paint chevronBackground1 = new Paint();
            chevronBackground1.setAntiAlias(true);
            chevronBackground1.setColor(0xffbb33);
            chevronBackground1.setAlpha(mGlowOffsetLeft);

            Paint chevronBackground2 = new Paint();
            chevronBackground2.setAntiAlias(true);
            chevronBackground2.setColor(0x99cc00);
            chevronBackground2.setAlpha(mGlowOffsetRight);

            // Paint status report only if settings allow
            if (mStatusMode != 0) {





                    }
                    canvas.drawCircle (snap.x, snap.y, snap.radius + wobble + (snap.active ? mAnimators[ANIMATOR_SNAP_GROW].fraction *

                    canvas.drawCircle (snap.x, snap.y, snap.radius + (snap.active ? mAnimators[ANIMATOR_SNAP_GROW].fraction *


                    canvas.drawCircle (snap.x, snap.y, (snap.active ? mAnimators[ANIMATOR_SNAP_GROW].fraction *

                            Math.max(getWidth(), getHeight()) : 0), mSnapBackground);

                    float snapDistanceX = snap.x-mX;
                    float snapDistanceY = snap.y-mY;
                    float snapDistance = (float)Math.sqrt(Math.pow(snapDistanceX, 2) + Math.pow(snapDistanceY, 2));
                    float snapTouch = snapDistance < mSnapRadius * 7 ? 200 - (snapDistance * (200 - snap.alpha) / (mSnapRadius * 7)) : snap.alpha;

                    mSnapBackground.setAlpha((int)(snapTouch));
                    int len = (int)(snap.radius * 1.3f);
                    int thick = (int)(len * 0.2f);

                    Path plus = new Path();
                    plus.addRect(snap.x - len / 2, snap.y - thick / 2, snap.x + len / 2, snap.y + thick / 2, Path.Direction.CW);
                    plus.addRect(snap.x - thick / 2, snap.y - len / 2, snap.x + thick / 2, snap.y + len / 2, Path.Direction.CW);
                    canvas.drawPath(plus, mSnapBackground);
                }
            }


            // Draw base menu
            for (PieItem item : mItems) {
                if (!canItemDisplay(item)) continue;
                drawItem(canvas, item);
            }


            // Paint status report only if settings allow
            if (mStatusMode != -1 && !mNavbarZero) {


                // Draw chevron rings
                mChevronBackgroundLeft.setAlpha((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * mGlowOffsetLeft / 2 * (mPanelOrientation == Gravity.TOP ? 0.2 : 1)));
                mChevronBackgroundRight.setAlpha((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * mGlowOffsetRight * (mPanelOrientation == Gravity.TOP ? 0.2 : 1)));

                if (mStatusPanel.getCurrentViewState() != PieStatusPanel.QUICK_SETTINGS_PANEL) {
                    state = canvas.save();
                    canvas.rotate(90, mCenter.x, mCenter.y);
                    for (int i=0; i < CHEVRON_FRAGMENTS + 1; i++) {
                        canvas.drawPath(mChevronPathLeft[i], mChevronBackgroundLeft);
                    }
                    canvas.restoreToCount(state);
                }

                if (mStatusPanel.getCurrentViewState() != PieStatusPanel.NOTIFICATIONS_PANEL) {
                    state = canvas.save();
                    canvas.rotate(180 + (1-mAnimators[ANIMATOR_BATTERY_METER].fraction) * 90, mCenter.x, mCenter.y);
                    canvas.drawPath(mChevronPathRight, mChevronBackgroundRight);
                    canvas.restoreToCount(state);
                }





            // Paint status report only if settings allow
            if (mStatusMode != 0) {


                // Draw Battery
                mBatteryBackground.setAlpha((int)(mAnimatedFraction[ANIMATOR_ACC_SPEED15] * 0x22));
                mBatteryJuice.setAlpha((int)(mAnimatedFraction[ANIMATOR_ACC_SPEED15] * 0x88));

                state = canvas.save();
                canvas.rotate(90 + (1-mAnimatedFraction[ANIMATOR_ACC_INC_1]) * 1000, mCenter.x, mCenter.y);
                canvas.drawPath(mBatteryPathBackground, mBatteryBackground);
                canvas.restoreToCount(state);

                state = canvas.save();
                canvas.rotate(90, mCenter.x, mCenter.y);
                canvas.drawPath(mBatteryPathJuice, mBatteryJuice);
                canvas.restoreToCount(state);

                // Draw clock && AM/PM
                state = canvas.save();
                canvas.rotate(mClockTextRotation, mCenter.x, mCenter.y);

                mClockPaint.setAlpha((int)(mAnimatedFraction[ANIMATOR_DEC_SPEED30] * 0xcc));
                float lastPos = 0;
                for(int i = 0; i < mClockText.length(); i++) {
                    canvas.drawTextOnPath("" + mClockText.charAt(i), mStatusPath, lastPos, mClockOffset, mClockPaint);
                    lastPos += mClockTextOffsets[i];
                }

                // Better not show inverted junk for top pies
                if (mPanelOrientation != Gravity.TOP) {



                // Better not show inverted junk for top pies
                if (mPanelOrientation != Gravity.TOP) {


                    // Draw Battery
                    mBatteryBackground.setAlpha((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * 0x22));
                    mBatteryJuice.setAlpha((int)(mAnimators[ANIMATOR_ACC_SPEED15].fraction * 0x88));

                    state = canvas.save();
                    canvas.rotate(90, mCenter.x, mCenter.y);
                    canvas.drawPath(mBatteryPathBackground, mBatteryBackground);
                    canvas.restoreToCount(state);

                    state = canvas.save();
                    canvas.rotate(90, mCenter.x, mCenter.y);
                    canvas.drawPath(mBatteryPathJuice, mBatteryJuice);
                    canvas.restoreToCount(state);

                    // Draw clock && AM/PM
                    state = canvas.save();
                    canvas.rotate(mClockTextRotation - (1-mAnimators[ANIMATOR_DEC_SPEED15].fraction) * 90, mCenter.x, mCenter.y);

                    mClockPaint.setAlpha((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * 0xcc));
                    float lastPos = 0;
                    for(int i = 0; i < mClockText.length(); i++) {
                        canvas.drawTextOnPath("" + mClockText.charAt(i), mStatusPath, lastPos, mClockOffset, mClockPaint);
                        lastPos += mClockTextOffsets[i];
                    }

                    mAmPmPaint.setAlpha((int)(mAnimators[ANIMATOR_DEC_SPEED15].fraction * 0xaa));
                    canvas.drawTextOnPath(mClockTextAmPm, mStatusPath, lastPos - mClockTextAmPmSize, mAmPmOffset, mAmPmPaint);
                    canvas.restoreToCount(state);

                    // Device status information and date
                    mStatusPaint.setAlpha((int)(mAnimators[ANIMATOR_ACC_SPEED15].fraction * 0xaa));
                    
                    state = canvas.save();
                    canvas.rotate(mPanel.getDegree() + 180 + (1-mAnimators[ANIMATOR_DEC_SPEED15].fraction) * 90, mCenter.x, mCenter.y);
                    if (mPolicy.supportsTelephony()) {
                        canvas.drawTextOnPath(mPolicy.getNetworkProvider(), mStatusPath, 0, mStatusOffset * 4, mStatusPaint);
                    }
                    canvas.drawTextOnPath(mPolicy.getSimpleDate(), mStatusPath, 0, mStatusOffset * 3, mStatusPaint);
                    canvas.drawTextOnPath(mPanel.getBar().getNotificationData().size() + " " + mContext.getString(R.string.status_bar_latest_events_title).toUpperCase(), mStatusPath, 0, mStatusOffset * 2, mStatusPaint);
                    canvas.drawTextOnPath(mContext.getString(R.string.quick_settings_wifi_label).toUpperCase() + ": " + mPolicy.getWifiSsid(), mStatusPath, 0, mStatusOffset * 1, mStatusPaint);
                    canvas.drawTextOnPath(mPolicy.getBatteryLevelReadable(), mStatusPath, 0, mStatusOffset * 0, mStatusPaint);
                    canvas.restoreToCount(state);

                    state = canvas.save();
                    canvas.rotate(mPanel.getDegree() + 180, mCenter.x, mCenter.y);

                    // Notifications
                    if (mStatusPanel.getCurrentViewState() != PieStatusPanel.NOTIFICATIONS_PANEL) {

                        for (int i = 0; i < mNotificationCount && i < 10; i++) {
                            mNotificationPaint.setAlpha((int)(mAnimators[ANIMATOR_ACC_INC_1 + i].fraction * mGlowOffsetRight));

                            canvas.drawTextOnPath(mNotificationText[i], mNotificationPath[i], 0, 0, mNotificationPaint);

                            int IconState = canvas.save();
                            int posX = (int)(mCenter.x + mNotificationsRadius + i * mNotificationsRowSize);
                            int posY = (int)(mCenter.y - mNotificationIconSize * 1.4f);
                            int iconCenter = mNotificationIconSize / 2;

                            canvas.rotate(90, posX + iconCenter, posY + iconCenter);
                            canvas.drawBitmap(mNotificationIcon[i], null, new Rect(posX, posY, posX +
                                    mNotificationIconSize,posY + mNotificationIconSize), mNotificationPaint);
                            canvas.restoreToCount(IconState);
                        }
                    }
                    canvas.restoreToCount(state);
                }
            }




        }
    }

    private void drawItem(Canvas canvas, PieItem item) {
        if (item.getView() != null) {

            Paint p = item.isSelected() ? mSelectedPaint : mNormalPaint;
            if (!mItems.contains(item)) {
                p = item.isSelected() ? mSelectedPaint : mSubPaint;
            }
            int state = canvas.save();


            if (onTheTop()) {
                canvas.scale(-1, 1);
            }
            float r = getDegrees(item.getStartAngle()) - 270; // degrees(0)
            canvas.rotate(r, mCenter.x, mCenter.y);
            canvas.drawPath(mPath, p);
            canvas.restoreToCount(state);
            // draw the item view
            View view = item.getView();

            canvas.rotate(getDegrees(item.getStartAngle()) + mPanel.getDegree(), mCenter.x, mCenter.y);
            canvas.drawPath(mPath, item.isSelected() ? mSelectedPaint : mNormalPaint);

            canvas.rotate(getDegrees(item.getStartAngle())
                        + mPanel.getDegree(), mCenter.x, mCenter.y);

            canvas.drawPath(item.getPath(), item.isSelected() ? mSelectedPaint : mNormalPaint);


            canvas.drawPath(item.getPath(), item.isSelected() ? mPieSelected : mPieBackground);




            canvas.drawPath(item.getPath(), mPieOutlines);


            canvas.drawPath(item.getPath(), mPieOutlines);

            canvas.restoreToCount(state);


            state = canvas.save();
            ImageView view = (ImageView)item.getView();
            canvas.translate(view.getX(), view.getY());


            canvas.rotate(getDegrees(item.getStartAngle() + item.getSweep() / 2) + mPanel.getDegree(), view.getWidth() / 2, view.getHeight() / 2);


            int state = canvas.save();
            canvas.rotate(getDegrees(item.getStartAngle())
                        + mPanel.getDegree(), mCenter.x, mCenter.y);
            canvas.drawPath(item.getPath(), item.isSelected() ? mPieSelected : mPieBackground);
            canvas.drawPath(item.getPath(), mPieOutlines);
            canvas.restoreToCount(state);

            state = canvas.save();
            ImageView view = (ImageView)item.getView();
            canvas.translate(view.getX(), view.getY());
            canvas.rotate(getDegrees(item.getStartAngle()
                    + item.getSweep() / 2) + mPanel.getDegree(),
                    view.getWidth() / 2, view.getHeight() / 2);


            view.draw(canvas);
            canvas.restoreToCount(state);
        }
    }

    private Path makeSlice(float start, float end, int outer, int inner, Point center) {
        return makeSlice(start, end, outer, inner, center, 0, true);
    }

    private Path makeSlice(float start, float end, int outer, int inner, Point center, float narrow, boolean bothEnds) {
        RectF bb = new RectF(center.x - outer, center.y - outer, center.x + outer, center.y + outer);
        RectF bbi = new RectF(center.x - inner, center.y - inner, center.x + inner, center.y + inner);
        Path path = new Path();
        path.arcTo(bb, start, end - start, true);
        path.arcTo(bbi, end + narrow, start - end - (bothEnds ? narrow : narrow*2));
        path.close();
        return path;
    }

    // touch handling for pie
    @Override
    public boolean onTouchEvent(MotionEvent evt) {



        float x = evt.getX();
        float y = evt.getY();
        int orient = mPanel.getOrientation();

        int distance = (int)Math.abs(orient == Gravity.TOP || orient == Gravity.BOTTOM ? y : x);
        int action = evt.getActionMasked();

        int distance = (int)Math.abs(orient == Gravity.TOP || orient == Gravity.BOTTOM ? y : x);    
        int shadeTreshold = getHeight() - mTouchOffset * 10;
        boolean pieTreshold = distance > mTouchOffset && distance < (int)(mRadius + mRadiusInc) * 2.5f;

        if (evt.getPointerCount() > 1) return true;



        if (evt.getPointerCount() > 1) return true;



        float x = evt.getRawX();
        float y = evt.getRawY();
        float distanceX = mCenter.x-x;
        float distanceY = mCenter.y-y;

        mX = evt.getRawX();
        mY = evt.getRawY();
        float distanceX = mCenter.x-mX;
        float distanceY = mCenter.y-mY;

        mCenterDistance = (float)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));




        float shadeTreshold = mRadius + mRadiusInc + mTouchOffset * 7.65f; 
        boolean pieTreshold = distanceY < shadeTreshold;


        float shadeTreshold = mOuterChevronRadius; 

        final boolean hapticFeedback = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;


        int action = evt.getActionMasked();
        if (MotionEvent.ACTION_DOWN == action) {

                mPanel.show(true);
                return true;

            // Open panel
            mPanel.show(true);
            animateIn();




        float shadeTreshold = mOuterChevronRadius; 
        
        int action = evt.getActionMasked();
        if (MotionEvent.ACTION_DOWN == action) {
            // Open panel
            animateIn();

        } else if (MotionEvent.ACTION_UP == action) {
            if (mOpen) {
                PieItem item = mCurrentItem;










                if (!handled && (item != null) && (item.getView() != null)) {


                int orient = mPanel.getOrientation();
                int distance = (int)Math.abs(orient == Gravity.TOP || orient == Gravity.BOTTOM ? y : x);
                if (!handled && (item != null) && (item.getView() != null) && (distance > mTouchOffset && distance
                        < (int)(mRadius + mRadiusInc) * 2.5f) ) {

                    if ((item == mOpenItem) || !mAnimating) {
                        item.getView().performClick();



                // Lets put the notification panel back
                hideNotificationsPanel();

                // Open the notification shade
                if (mPanelActive) {
                    mPanelParent.removeView(mPanel.getBar().getNotificationRowLayout());
                    mScrollView.addView(mPanel.getBar().getNotificationRowLayout());
                    mWindowManager.addView(mContainer, getNotificationsPanelLayoutParams());
                    mPanelParentChanged = true;
                    if(hapticFeedback) mVibrator.vibrate(2);

                    mContentFrame.setBackgroundColor(mBackgroundOpacity);
                    ValueAnimator mAlphaAnimation  = ValueAnimator.ofInt(0, 1);
                    mAlphaAnimation.addUpdateListener(new AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mScrollView.setX(-(int)((1-animation.getAnimatedFraction()) * getWidth()*1.5));
                            mContentFrame.setBackgroundColor((int)(animation.getAnimatedFraction() * 0xDD) << 24);
                            invalidate();
                        }
                    });
                    mAlphaAnimation.setDuration(1000);
                    mAlphaAnimation.setInterpolator(new DecelerateInterpolator());
                    mAlphaAnimation.start();

                switch(mFlipViewState) {
                    case NOTIFICATIONS_PANEL:
                        showPanel(mNotificationPanel);
                        break;
                    case QUICK_SETTINGS_PANEL:
                        showPanel(mQS);
                        break;


                hidePanels(true);
                if (mFlipViewState != -1) {
                    switch(mFlipViewState) {
                        case NOTIFICATIONS_PANEL:
                            mCurrentViewState = NOTIFICATIONS_PANEL;
                            showPanel(mNotificationPanel);




                // Check for snap points first
                for (int i = 0; i < 4; i++) {
                    SnapPoint snap = mSnapPoint[i];
                    if (snap.active) {
                        if(hapticFeedback) mVibrator.vibrate(2);
                        deselect();
                        animateOut();
                        mPanel.reorient(snap.gravity);
                        return true;
                    }
                }



                // Activate any panels?




                mStatusPanel.hidePanels(true);

                if (mStatusPanel.getFlipViewState() != -1) {
                    switch(mStatusPanel.getFlipViewState()) {
                        case PieStatusPanel.NOTIFICATIONS_PANEL:
                            mStatusPanel.setCurrentViewState(PieStatusPanel.NOTIFICATIONS_PANEL);
                            mStatusPanel.showNotificationsPanel();




                            break;
                        case PieStatusPanel.QUICK_SETTINGS_PANEL:
                            mStatusPanel.setCurrentViewState(PieStatusPanel.QUICK_SETTINGS_PANEL);
                            mStatusPanel.showTilesPanel();
                            break;
                    }


                }
      


                // Check for actions

                if (item != null && item.getView() != null) {
                    if(distance > mTouchOffset && distance < (int)(mRadius + mRadiusInc) * 2.5f) {
                        mVibrator.vibrate(5);
                        item.getView().performClick();
                    } else if (distance > getHeight() - mTouchOffset) {
                        mPanelParent.removeView(mPanel.getBar().getNotificationRowLayout());
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL,
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                                        | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                                PixelFormat.TRANSLUCENT);

                        mPanel.getBar().getWindowManager().addView(mPanel.getBar().getNotificationRowLayout(), lp);


                    }
                }
            }
            mPanel.show(false);


                        mScrollView.addView(mPanel.getBar().getNotificationRowLayout());

                        mPanel.getBar().getWindowManager().addView(mScrollView, lp);


                        mWindowManager.addView(mContainer, lp);

                        mPanelParentChanged = true;
                    }



                switch(mStatusPanel.getFlipViewState()) {
                    case PieStatusPanel.NOTIFICATIONS_PANEL:
                        mStatusPanel.setCurrentViewState(PieStatusPanel.NOTIFICATIONS_PANEL);
                        mStatusPanel.showNotificationsPanel();
                        break;
                    case PieStatusPanel.QUICK_SETTINGS_PANEL:
                        mStatusPanel.setCurrentViewState(PieStatusPanel.QUICK_SETTINGS_PANEL);
                        mStatusPanel.showTilesPanel();
                    break;

                }
      

                // Check for click actions
                if (item != null && item.getView() != null && mCenterDistance < shadeTreshold) {
                    if(mHapticFeedback) mVibrator.vibrate(2);
                    item.getView().performClick();




                }
            }

            // Say good bye
            deselect();
            animateOut();




            return true;
        } else if (MotionEvent.ACTION_MOVE == action) {

            boolean snapActive = false;
            for (int i = 0; i < 3; i++) {
                SnapPoint snap = mSnapPoint[i];                
                float snapDistanceX = snap.x-mX;
                float snapDistanceY = snap.y-mY;
                float snapDistance = (float)Math.sqrt(Math.pow(snapDistanceX, 2) + Math.pow(snapDistanceY, 2));

                if (snapDistance < mSnapRadius) {
                    snap.alpha = 60;
                    if (!snap.active) {
                        mAnimators[ANIMATOR_SNAP_GROW].cancel();
                        mAnimators[ANIMATOR_SNAP_GROW].animator.start();
                        if(mHapticFeedback) mVibrator.vibrate(2);
                    }
                    snap.active = true;
                    snapActive = true;
                    mStatusPanel.setFlipViewState(-1);
                    mGlowOffsetLeft = 150;
                    mGlowOffsetRight = 150;
                } else {
                    if (snap.active) {
                        mAnimators[ANIMATOR_SNAP_GROW].cancel();
                    }
                    snap.alpha = 30;
                    snap.active = false;
                }
            }

            // Trigger the shades?
            if (mCenterDistance > shadeTreshold) {
                int state = -1;
                switch (mPanelOrientation) {
                    case Gravity.BOTTOM:
                        state = distanceX > 0 ? PieStatusPanel.QUICK_SETTINGS_PANEL : PieStatusPanel.NOTIFICATIONS_PANEL;
                        break;
                    case Gravity.TOP:
                        state = distanceX > 0 ? PieStatusPanel.QUICK_SETTINGS_PANEL : PieStatusPanel.NOTIFICATIONS_PANEL;
                        break;
                    case Gravity.LEFT:
                        state = distanceY > 0 ? PieStatusPanel.QUICK_SETTINGS_PANEL : PieStatusPanel.NOTIFICATIONS_PANEL;
                        break;
                    case Gravity.RIGHT:
                        state = distanceY < 0 ? PieStatusPanel.QUICK_SETTINGS_PANEL : PieStatusPanel.NOTIFICATIONS_PANEL;
                        break;
                }



                if (state == PieStatusPanel.QUICK_SETTINGS_PANEL && mStatusPanel.getFlipViewState() != PieStatusPanel.QUICK_SETTINGS_PANEL
                        && mStatusPanel.getCurrentViewState() != PieStatusPanel.QUICK_SETTINGS_PANEL) {
                    mGlowOffsetRight = 100;
                    mGlowOffsetLeft = 255;
                    mStatusPanel.setFlipViewState(PieStatusPanel.QUICK_SETTINGS_PANEL);
                    if(hapticFeedback) mVibrator.vibrate(2);
                } else if (state == PieStatusPanel.NOTIFICATIONS_PANEL && mStatusPanel.getFlipViewState() != PieStatusPanel.NOTIFICATIONS_PANEL
                        && mStatusPanel.getCurrentViewState() != PieStatusPanel.NOTIFICATIONS_PANEL) {
                    mGlowOffsetRight = 255;
                    mGlowOffsetLeft = 100;
                    mStatusPanel.setFlipViewState(PieStatusPanel.NOTIFICATIONS_PANEL);
                    if(hapticFeedback) mVibrator.vibrate(2);



                if (!mNavbarZero) {
                    if (state == PieStatusPanel.QUICK_SETTINGS_PANEL && 
                            mStatusPanel.getFlipViewState() != PieStatusPanel.QUICK_SETTINGS_PANEL
                            && mStatusPanel.getCurrentViewState() != PieStatusPanel.QUICK_SETTINGS_PANEL) {
                        mGlowOffsetRight = mPanelOrientation != Gravity.TOP ? 150 : 255;;
                        mGlowOffsetLeft = mPanelOrientation != Gravity.TOP ? 255 : 150;
                        mStatusPanel.setFlipViewState(PieStatusPanel.QUICK_SETTINGS_PANEL);
                        if (mHapticFeedback && !snapActive) mVibrator.vibrate(2);
                    } else if (state == PieStatusPanel.NOTIFICATIONS_PANEL && 
                            mStatusPanel.getFlipViewState() != PieStatusPanel.NOTIFICATIONS_PANEL
                            && mStatusPanel.getCurrentViewState() != PieStatusPanel.NOTIFICATIONS_PANEL) {
                        mGlowOffsetRight = mPanelOrientation != Gravity.TOP ? 255 : 150;
                        mGlowOffsetLeft = mPanelOrientation != Gravity.TOP ? 150 : 255;
                        mStatusPanel.setFlipViewState(PieStatusPanel.NOTIFICATIONS_PANEL);
                        if (mHapticFeedback && !snapActive) mVibrator.vibrate(2);
                    }




                }
                deselect();
            }




            // Take back shade trigger if user decides to abandon his gesture
            if (distanceY < shadeTreshold) mPanelActive = false;


            PieItem item = findItem(getPolar(x, y));
            //if (pieTreshold) {
                // Check for onEnter separately or'll face constant deselect
                if (item != null && mCurrentItem != item) {
                    onEnter(item);
                }
            //} else {
            //    deselect();
            //}



            // Take back shade trigger if user decides to abandon his gesture


            if (distanceY < shadeTreshold) mFlipViewState = -1;


            // Check for onEnter separately or'll face constant deselect
            PieItem item = findItem(getPolar(x, y));
            if (item != null) {
                if (distanceY < shadeTreshold && distance > mTouchOffset * 2.5f) {
                    onEnter(item);
                } else {
                    deselect();

            if (distance < shadeTreshold) {

            if (mCenterDistance < shadeTreshold) {


            // Take back shade trigger if user decides to abandon his gesture
            if (mCenterDistance < shadeTreshold) {

                mStatusPanel.setFlipViewState(-1);
                mGlowOffsetLeft = 150;
                mGlowOffsetRight = 150;

                // Check for onEnter separately or'll face constant deselect
                PieItem item = findItem(getPolar(mX, mY));
                if (item != null) {
                    if (mCenterDistance < shadeTreshold && mCenterDistance > (mInnerPieRadius * 0.75f)) {
                        onEnter(item);
                    } else {
                        deselect();
                    }


                }
            }


                }
            }

            invalidate();
        }
        // always re-dispatch event
        return false;
    }

    boolean mPanelActive = false;



    private void onEnter(PieItem item) {
        if (mCurrentItem == item) return;

        // deselect
        if (mCurrentItem != null) {
            mCurrentItem.setSelected(false);
        }
        if (item != null) {
            // clear up stack
            playSoundEffect(SoundEffectConstants.CLICK);
            item.setSelected(true);
            mCurrentItem = item;
        } else {
            mCurrentItem = null;
        }

    }

    private void deselect() {
        if (mCurrentItem != null) {
            mCurrentItem.setSelected(false);
        }
        mCurrentItem = null;
    }




    private PointF getPolar(float x, float y) {
        PointF res = new PointF();
        // get angle and radius from x/y
        res.x = (float) Math.PI / 2;
        x = mCenter.x - x;
        if (mCenter.x < mSlop) {
            x = -x;
        }
        y = mCenter.y - y;
        res.y = (float) Math.sqrt(x * x + y * y);
        if (y > 0) {
            res.x = (float) Math.asin(x / res.y);
        } else if (y < 0) {
            res.x = (float) (Math.PI - Math.asin(x / res.y ));
        }
        return res;

    private float getPolar(double x, double y) {
        PointF size = mPanel.getSize();

    private float getPolar(float x, float y) {
        float deltaY = mCenter.y - y;
        float deltaX = mCenter.x - x;

        float adjustAngle = 0;

        int orient = mPanel.getOrientation();
        switch(orient) {

        float adjustAngle = 0;;
        switch(mPanelOrientation) {


    private float getPolar(float x, float y) {
        float deltaY = mCenter.y - y;
        float deltaX = mCenter.x - x;
        float adjustAngle = 0;;
        switch(mPanelOrientation) {

            case Gravity.TOP:
            case Gravity.LEFT:
                adjustAngle = 90;
                break;
            case Gravity.RIGHT:
                adjustAngle = -90;
                break;
        }



        return -(((float)(Math.acos((orient == Gravity.TOP || orient == Gravity.BOTTOM ? x : y) /
                Math.sqrt(x * x + y * y)) * 180 / Math.PI) - 90) / 10);


        return (adjustAngle + (float)Math.atan2(orient == Gravity.TOP ? deltaY : deltaX,
                orient == Gravity.TOP ? deltaX : deltaY) * 180 / (float)Math.PI)
                * (orient == Gravity.TOP ? -1 : 1) * (float)Math.PI / 180;


        return (adjustAngle + (float)Math.atan2(mPanelOrientation == Gravity.TOP ? deltaY : deltaX,
                mPanelOrientation == Gravity.TOP ? deltaX : deltaY) * 180 / (float)Math.PI)
                * (mPanelOrientation == Gravity.TOP ? -1 : 1) * (float)Math.PI / 180;


        return (adjustAngle + (float)Math.atan2(mPanelOrientation == Gravity.TOP ? deltaY : deltaX,
                mPanelOrientation == Gravity.TOP ? deltaX : deltaY) * 180 / (float)Math.PI)
                * (mPanelOrientation == Gravity.TOP ? -1 : 1) * (float)Math.PI / 180;

    }

    private PieItem findItem(float polar) {
        if (mItems != null) {
            int c = 0;
            for (PieItem item : mItems) {
                if (!canItemDisplay(item)) continue;
                if (inside(polar, item)) {
                    return item;
                }
            }
        }
        return null;
    }




    private boolean inside(PointF polar, float offset, PieItem item) {
        float sweep = polar.x < 0 ? -(item.getSweep()/2) : item.getSweep()/2;
        return (item.getStartAngle() < polar.x + sweep)
        && (item.getStartAngle() + item.getSweep() > polar.x + sweep);

    private boolean inside(float polar, float offset, PieItem item) {

    private boolean inside(float polar, PieItem item) {

        return (item.getStartAngle() < polar)
        && (item.getStartAngle() + item.getSweep() > polar);


    private boolean inside(float polar, PieItem item) {
        return (item.getStartAngle() < polar)
        && (item.getStartAngle() + item.getSweep() > polar);

    }
}
