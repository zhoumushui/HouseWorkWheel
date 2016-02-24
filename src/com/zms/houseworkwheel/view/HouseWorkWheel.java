package com.zms.houseworkwheel.view;

import java.util.Random;

import com.zms.houseworkwheel.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HouseWorkWheel extends RelativeLayout {

	private RelativeLayout relativeLayout;
	private ImageView imagePointer;
	private TextView text1, text2, text3, text4, text5, text6;

	private Context context;

	/** 指针转一圈所需要的时间 */
	private static final long ONE_WHEEL_TIME = 500;
	/** 开始转动时候的角度，初始值为0 */
	private int degreeStart = 0;

	/** 指针转圈圈数数据源 */
	private int[] laps = { 3, 4, 5, 6 };
	/** 指针所指向的角度数据 */
	private int[] angles = { 0, 60, 120, 180, 240, 300 };

	/** 内容个数 */
	private int itemCount = 6;

	/** 监听动画状态的监听器 */
	private AnimationListener animationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			String name = arrayHouseWork[degreeStart % 360 / 60];
			Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
		}
	};

	public HouseWorkWheel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialView(context);
		this.context = context;
	}

	public HouseWorkWheel(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HouseWorkWheel(Context context) {
		this(context, null);
	}

	private void initialView(final Context context) {
		if (relativeLayout == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			relativeLayout = (RelativeLayout) inflater.inflate(
					R.layout.view_lucky_wheel, this);
		}

		text1 = (TextView) findViewById(R.id.text1);
		text1.setText(arrayHouseWork[0]);
		text2 = (TextView) findViewById(R.id.text2);
		text2.setText(arrayHouseWork[1]);
		text3 = (TextView) findViewById(R.id.text3);
		text3.setText(arrayHouseWork[2]);
		text4 = (TextView) findViewById(R.id.text4);
		text4.setText(arrayHouseWork[3]);
		text5 = (TextView) findViewById(R.id.text5);
		text5.setText(arrayHouseWork[4]);
		text6 = (TextView) findViewById(R.id.text6);
		text6.setText(arrayHouseWork[5]);

		imagePointer = (ImageView) relativeLayout
				.findViewById(R.id.imagePointer);

		imagePointer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int lap = laps[(int) (Math.random() * 4)];
				int angle = angles[countProbability(arrayProbability)];
				int degreeIncrease = lap * 360 + angle; // 每次转圈角度增量
				// 初始化旋转动画
				RotateAnimation rotateAnimation = new RotateAnimation(
						degreeStart, degreeStart + degreeIncrease,
						RotateAnimation.RELATIVE_TO_SELF, 0.5f,
						RotateAnimation.RELATIVE_TO_SELF, 0.5f);
				degreeStart += degreeIncrease; // 将最后的角度赋值给startDegree作为下次转圈的初始角度
				long time = (lap + angle / 360) * ONE_WHEEL_TIME; // 计算动画播放总时间
				rotateAnimation.setDuration(time); // 设置动画播放时间
				rotateAnimation.setFillAfter(true); // 设置动画播放完后，停留在最后一帧画面上
				rotateAnimation.setInterpolator(context,
						android.R.anim.accelerate_decelerate_interpolator); // 设置动画的加速行为，是先加速后减速
				rotateAnimation.setAnimationListener(animationListener); // 设置动画的监听器
				imagePointer.startAnimation(rotateAnimation); // 开始播放动画
			}
		});

		int[] arrayCount = { 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < 10000; i++) {
			int temp = countProbability(arrayProbability);
			switch (temp) {
			case 0:
				arrayCount[0]++;
				break;

			case 1:
				arrayCount[1]++;
				break;

			case 2:
				arrayCount[2]++;
				break;

			case 3:
				arrayCount[3]++;
				break;

			case 4:
				arrayCount[4]++;
				break;

			case 5:
				arrayCount[5]++;
				break;

			default:
				break;
			}
		}

		Log.v("AZ", arrayCount[0] + "\t" + arrayCount[1] + "\t" + arrayCount[2]
				+ "\t" + arrayCount[3] + "\t" + arrayCount[4] + "\t"
				+ arrayCount[5]);

	}

	/** 转盘内容数组 */
	private String[] arrayHouseWork = { "扫地", "洗衣服", "休息", "做饭", "洗碗", "休息" };
	/** 概率数组(百分之) */
	private int[] arrayProbability = { 10, 10, 480, 10, 10, 480 };

	private int countProbability(int[] arrayProbability) {
		int temNumber = 1 + new Random().nextInt(1000); // 1-1000
		int returnNumber;

		if (temNumber <= arrayProbability[0]) {
			returnNumber = 0;
		} else if (temNumber <= arrayProbability[0] + arrayProbability[1]) {
			returnNumber = 1;
		} else if (temNumber <= arrayProbability[0] + arrayProbability[1]
				+ arrayProbability[2]) {
			returnNumber = 2;
		} else if (temNumber <= arrayProbability[0] + arrayProbability[1]
				+ arrayProbability[2] + arrayProbability[3]) {
			returnNumber = 3;
		} else if (temNumber <= arrayProbability[0] + arrayProbability[1]
				+ arrayProbability[2] + arrayProbability[3]
				+ arrayProbability[4]) {
			returnNumber = 4;
		} else {
			returnNumber = 5;
		}
		return returnNumber;
	}

}
