package py.com.alemiranda95.zemoga.ui.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import py.com.alemiranda95.zemoga.utils.extension.Inflate

abstract class BaseActivity <T: ViewBinding>(
    private val inflate: Inflate<T>
) : AppCompatActivity() {

    private var _binding: T? = null
    val binding get() = _binding!!

    lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = inflate.invoke(layoutInflater, null, false)
        setContentView(binding.root)

        setViews()
        setObservers()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setViews()

    abstract fun setObservers()

    abstract fun setListeners()
}