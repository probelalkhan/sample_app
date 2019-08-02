package net.simplifiedcoding.ui.home.detail

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.employee_detail_fragment.*
import net.simplifiedcoding.R
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.databinding.EmployeeDetailFragmentBinding
import net.simplifiedcoding.databinding.EmployeeListFragmentBinding
import net.simplifiedcoding.ui.home.list.EmployeeListViewModel
import net.simplifiedcoding.util.Coroutines
import net.simplifiedcoding.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EmployeeDetailFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: EmployeeDetailViewModelFactory by instance()

    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var viewModel: EmployeeDetailViewModel
    var currentPhotoPath: String? = null
    var lastModified: String? = null

    var employee: Employee? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: EmployeeDetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.employee_detail_fragment, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(EmployeeDetailViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(EmployeeDetailViewModel::class.java)

        arguments?.let {
            val args = EmployeeDetailFragmentArgs.fromBundle(it)
            employee = args.employee
        }

        employee?.let {
            viewModel.findEmployee(it.id)
        }


        button_take_picture.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bmp = BitmapFactory.decodeFile(currentPhotoPath, options)
            image_view.setImageBitmap(bmp)
            employee?.image = currentPhotoPath
            employee?.imageTime = lastModified
            Coroutines.io {
                employee?.let { viewModel.updateEmployee(it) }
            }
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "net.simplifiedcoding.fileprovider",
                        it
                    )

                    lastModified = Date(it.lastModified()).toString()

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
}
