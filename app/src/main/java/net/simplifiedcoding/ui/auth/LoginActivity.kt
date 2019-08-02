package net.simplifiedcoding.ui.auth

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import net.simplifiedcoding.R
import net.simplifiedcoding.databinding.ActivityLoginBinding
import net.simplifiedcoding.ui.home.HomeActivity
import net.simplifiedcoding.ui.home.HomeViewModelFactory
import net.simplifiedcoding.util.hide
import net.simplifiedcoding.util.show
import net.simplifiedcoding.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), LoginListener, KodeinAware {

    override val kodein by kodein()
    private val factory: LoginViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.loginListener = this


        viewModel.getCurrentUser().observe(this, Observer {
            if (it != null)
                login()
        })


        object : CountDownTimer(3000, 100) {
            override fun onFinish() {
                progress_bar_loading.visibility = View.GONE
                layout_logo.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSplashText))
                bookIconImageView.setImageResource(R.drawable.ic_tvs_dark)
                startAnimation()
            }

            override fun onTick(p0: Long) {}
        }.start()


    }


    private fun startAnimation() {
        bookIconImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                layout_login.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })
    }

    override fun onStarted() {
        progressbar.show()
    }

    override fun onSuccess() {
        progressbar.hide()
        login()
    }

    override fun onFailure(s: String) {
        progressbar.hide()
        layout_root.snackbar(s)
    }

    private fun login() {
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
        }
    }
}
