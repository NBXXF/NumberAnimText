package com.xxf.view.numberanimtext.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import com.xxf.view.numberanimtext.CharOrder
import com.xxf.view.numberanimtext.strategy.AlignAnimationStrategy
import com.xxf.view.numberanimtext.strategy.Direction
import com.xxf.view.numberanimtext.strategy.SameDirectionStrategy
import kotlinx.android.synthetic.main.activity_issue_14.*

/**
 * Created by 张宇 on 2019/5/24.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class Issue14Activity : FragmentActivity() {

    private val list = listOf("1", "21339", "12", "123319", "24", "6", "247",
        "5226", "63", "378", "234389", "12395", "2", "1289", "32212", "400")

    private var idx = 0

    private val handler = Handler(Looper.getMainLooper())

    private val loopAnimator = object : Runnable {

        override fun run() {
            tvFragmentTitle.setText(list[idx++ % list.size])
            handler.postDelayed(this, 2000L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_14)

        tvFragmentTitle.animationDuration = 1500L
        tvFragmentTitle.addCharOrder(CharOrder.Number)
        tvFragmentTitle.charStrategy = SameDirectionStrategy(
            Direction.SCROLL_DOWN,
            AlignAnimationStrategy(AlignAnimationStrategy.TextAlignment.Left)
        )
        tvFragmentTitle.post(loopAnimator)
    }
}