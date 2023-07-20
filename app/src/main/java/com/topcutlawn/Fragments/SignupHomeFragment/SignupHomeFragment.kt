package com.topcutlawn.Fragments.SignupHomeFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.equalinfotech.miiwey.utils.UtilsGoGokull
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Camerautils.FileCompressor
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.Fragments.ProfileFragment.PropertyPictureViewModel
import com.topcutlawn.MainActivity
import com.topcutlawn.R
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentSignupHomeBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class SignupHomeFragment : Fragment() {

    private var _binding: FragmentSignupHomeBinding? = null
    var attachFile: String = ""
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var mAppLoader: AppLoader? = null
    private val binding get() = _binding!!
    private val RECORD_REQUEST_CODE = 101
    var image1path = ""
    var SELECT_PICTURE = 200
    val REQUEST_CODE = 100
    var mCompressor: FileCompressor? = null
    var filepath_presc: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupHomeBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        binding.ivMenu.setOnClickListener {
            requireActivity().onBackPressed();
        }

        binding.ivHome.setOnClickListener {
            if (checkPermission() == true) {


                openCameraGallery()
            } else {
                setupPermissions()
            }

           /* var intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)*/
        }



        binding.signuphome.setOnClickListener {
            if (image1path.equals("")){
                Toast.makeText(requireContext(),"Please Upload Image",Toast.LENGTH_SHORT).show()
            }else{
              //  Toast.makeText(requireContext(),"Please Success",Toast.LENGTH_SHORT).show()
               // userimageupdate()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)

            }

           // findNavController().navigate(R.id.action_SignupHomeFragment_to_FirstFragment)
        }
        /*    binding.buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }*/
    }


    fun setupPermissions(): Boolean {
        val permissions =
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ))
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
            makeRequest()
        }
        return true
    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity() as Activity, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), RECORD_REQUEST_CODE
        )
    }


    fun checkPermission(): Boolean {
        val cameraPermission =
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    + ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ))
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }
    private fun openCameraGallery() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setCropMenuCropButtonTitle("Upload")
            }
        )
    }
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri


            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireActivity())
            val bitmap: Bitmap? = UtilsGoGokull.getBitmap(uriFilePath, requireContext())

            uriFilePath?.let { updateImage(bitmap, it) }
        } else {

            val exception = result.error
        }
    }
    private fun updateImage(bitmap: Bitmap?, filePath: String) {

        attachFile = filePath
        val bmImg = BitmapFactory.decodeFile(attachFile)
        binding.ivHome.setImageBitmap(bmImg)
        image1path=filePath
        userimageupdate()
    }


    private fun userimageupdate() {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        val multiPartRepeatString = "imageUpdate"
        var image1: MultipartBody.Part? = null

        val userId: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            SharedPreferenceUtils.getInstance(requireContext())
                .getStringValue(ConstantUtils.ID, "").toString()
        )

        if (!image1path.isNullOrEmpty()) {

            val file = File(image1path)
            val signPicBody =
                RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), file)
            image1 = MultipartBody.Part.createFormData("image", file.name, signPicBody)
        }


        var call = APIUtils.getServiceAPI()!!.update_virtual_picture(userId, image1)

        call.enqueue(object : Callback<UpdateVirtualImageModel> {
            override fun onResponse(
                call: Call<UpdateVirtualImageModel>,
                response: Response<UpdateVirtualImageModel>
            ) {
                try {

                    if (response.body()!!.status == "1") {

                        mAppLoader?.dismissDialog()
                        //  rlLoader!!.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Images updated successfully !",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Images updated successfully !",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAppLoader?.dismissDialog()
                        // rlLoader!!.visibility = View.GONE

                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Images updated successfully !",
                        Toast.LENGTH_SHORT
                    ).show()
                    mAppLoader?.dismissDialog()
                    // rlLoader!!.visibility = View.GONE
                }


            }

            override fun onFailure(
                call: Call<UpdateVirtualImageModel>,
                t: Throwable
            ) {
                mAppLoader?.dismissDialog()
                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Images updated successfully !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }


/*
    private fun userimageupdate() {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        val multiPartRepeatString = "imageUpdate"
        var image1: MultipartBody.Part? = null

        val userId: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            "23"
        )

        if (!image1path.isNullOrEmpty()) {

            val file = File(image1path)
            val signPicBody =
                RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), file)
            image1 = MultipartBody.Part.createFormData("image", file.name, signPicBody)
        }


        var call = APIUtils.getServiceAPI()!!.update_virtual_picture(userId, image1)

        call.enqueue(object : Callback<PropertyPictureViewModel.UpdateProfileImageResponse> {
            override fun onResponse(
                call: Call<PropertyPictureViewModel.UpdateProfileImageResponse>,
                response: Response<PropertyPictureViewModel.UpdateProfileImageResponse>
            ) {
                try {

                    if (response.body()!!.status == "1") {

                        mAppLoader?.dismissDialog()
                        //  rlLoader!!.visibility = View.GONE
                        findNavController().navigate(R.id.action_SignupHomeFragment_to_FirstFragment)
                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        mAppLoader?.dismissDialog()
                        // rlLoader!!.visibility = View.GONE

                    }

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
                    mAppLoader?.dismissDialog()
                    // rlLoader!!.visibility = View.GONE
                }


            }

            override fun onFailure(
                call: Call<PropertyPictureViewModel.UpdateProfileImageResponse>,
                t: Throwable
            ) {
                mAppLoader?.dismissDialog()
                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            binding.ivHome.setImageURI(data?.data) // handle chosen image

          //  onselectfromgallery(data!!)
            image1path=data?.data?.path!!




        }
    }
/*
    fun onselectfromgallery(data: Intent) {
        filepath_presc = data.data
        if (filepath_presc.toString() == null) {

        } else {
            try {
                Log.e("filepath", filepath_presc.toString() + "")
                var bitmap: Bitmap? = null
                var file = File(filepath_presc?.let { getRealPathFromUri(it) })
                try {
                    file = mCompressor!!.compressToFile(file)
                    bitmap = mCompressor!!.compressToBitmap(file)
                    image1path = file.path
                    //profileimage = bitmap?.let { getStringImage(it) }.toString()
                    //civProfileImage!!.setImageBitmap(bitmap)
                    */
/*if (NetworkUtils.checkInternetConnection(this@ProfileActivity)) {

                    }*//*




                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Log.e("exception1", e.toString())
                }

            } catch (e: java.lang.Exception) {
                Log.e("exception", e.toString())
                e.printStackTrace()
            }
        }
    }
*/


/*
    @SuppressLint("Range")

    fun getRealPathFromUri(uri: Uri): String? {
        var result = ""
        val documentID: String
        documentID = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val pathParts = uri.path!!.split("/".toRegex()).toTypedArray()
            pathParts[pathParts.size - 1]
        } else {
            val pathSegments = uri.lastPathSegment!!.split(":".toRegex()).toTypedArray()
            pathSegments[pathSegments.size - 1]
        }
        val mediaPath = MediaStore.Images.Media.DATA
        val imageCursor: Cursor? = contentResolver.query(
            uri,
            arrayOf(mediaPath),
            MediaStore.Images.Media._ID + "=" + documentID,
            null,
            null
        )
        if (imageCursor!!.moveToFirst()) {
            result = imageCursor.getString(imageCursor.getColumnIndex(mediaPath))
        }
        return result
    }
*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}