package com.topcutlawn.Fragments.ProfileFragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.equalinfotech.miiwey.utils.UtilsGoGokull
import com.squareup.picasso.Picasso
import com.topcutlawn.API.APIUtils
import com.topcutlawn.Camerautils.FileCompressor
import com.topcutlawn.Fragments.HomeFragment.HomeActivity
import com.topcutlawn.Utils.AppLoader
import com.topcutlawn.Utils.ConstantUtils
import com.topcutlawn.Utils.NetworkUtils
import com.topcutlawn.Utils.SharedPreferenceUtils
import com.topcutlawn.databinding.FragmentProfileBinding
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var mAppLoader: AppLoader? = null
    var user_id = ""
    var name = ""
    var dob = ""
    var dimenlawn = ""
    var total = ""
    var email = ""
    var phone = ""
    var address = ""

    var attachFile: String = ""
    var REQUEST_CODE: Int = 0
    var userChoosenTask = ""
    private var PICK_IMAGE_REQUEST: Int = 1
    private var CAMERA_REQUEST: Int = 1
    var currentPhotoPath = ""
    var mCompressor: FileCompressor? = null
    var image1path = ""
    var profileimage = ""
    var civProfileImage: CircleImageView? = null
    var filepath_presc: Uri? = null
    var type = ""
    private val RECORD_REQUEST_CODE = 101

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var propertyPictureAdapter: PropertyPictureAdapter
    private lateinit var picturelist: List<PropertyPictureViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        mAppLoader = AppLoader(requireActivity() as Activity?)
        user_id =
            SharedPreferenceUtils.getInstance(requireContext()).getStringValue(ConstantUtils.ID, "")
                .toString()

        binding.ivCamera.setOnClickListener {
            /*   if (clickCount == 1)
               {*/
            if (checkPermission() == true) {

                openCameraGallery()
            } else {
                setupPermissions()
            }

        }

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // picturepropertylist()
        profile_details()


        binding.rvPropertypicture
        //onClick()

/*        binding.rvPropertypicture.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPropertypicture.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        propertyPictureAdapter = PropertyPictureAdapter(picturelist, requireContext())

        binding.rvPropertypicture.adapter = propertyPictureAdapter*/

        binding.etdob.setOnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (year.toString()+ "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString())
                    binding.etdob.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }




        binding.tvUpdateprofile.setOnClickListener {
            name = binding.etName.text.toString()
            dob = binding.etdob.text.toString()
            address = binding.etaddress.text.toString()
            dimenlawn = binding.etdimen.text.toString()
            total = binding.ettotal.text.toString()


            if (binding.etName.text.toString().isNullOrEmpty()) {
                binding.etName.error = "Enter your Name"
            } else if (binding.etdob.text.toString().isNullOrEmpty()) {
                binding.etdob.error = "Enter your Date Of Birth"
            } else if (binding.etaddress.text.toString().isNullOrEmpty()) {
                binding.etaddress.error = "Enter your Address"
            } else if (binding.etdimen.text.toString().isNullOrEmpty()) {
                binding.etdimen.error = "Enter Dimensions Lawn"
            } else if (binding.ettotal.text.toString().isNullOrEmpty()) {
                binding.ettotal.error = "Enter Total Square of Lawn"
            } else {
                if (NetworkUtils.checkInternetConnection(requireContext())) {

                    updatedetails(
                        binding.etName.text.toString(),
                        binding.etdob.text.toString(),
                        binding.etaddress.text.toString(),
                        binding.etdimen.text.toString(),
                        binding.ettotal.text.toString(),
                        SharedPreferenceUtils.getInstance(requireContext())
                            .getStringValue(ConstantUtils.ID, "").toString(),
                    )
                }

            }


        }


    }

    fun profile_details() {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("user_id", user_id)

        var userdetails = APIUtils.getServiceAPI()!!.editprofile(stringStringHashMap)
        userdetails.enqueue(object : Callback<PropertyPictureViewModel.EditProfileResponse> {
            override fun onResponse(
                call: Call<PropertyPictureViewModel.EditProfileResponse>,
                response: Response<PropertyPictureViewModel.EditProfileResponse>
            ) {
                try {
                    var propertylist = ArrayList<PropertyPictureViewModel.EditProfileResponse.VirtualPicture>()
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()

                            binding.etName.setText(response.body()!!.user_details.name)
                            binding.tvName.setText(response.body()!!.user_details.name)
                            binding.tvEmailid.setText(response.body()!!.user_details.email)
                            binding.etemail.setText(response.body()!!.user_details.email)
                            binding.etdob.setText(response.body()!!.user_details.dob)
                            binding.etphone.setText(response.body()!!.user_details.mobile)
                            binding.etaddress.setText(response.body()!!.user_details.address)
                            binding.etdimen.setText(response.body()!!.user_details.dimension_lawn)
                            binding.ettotal.setText(response.body()!!.user_details.total_square_lawn)

                            val url = response.body()!!
                                .user_details.image.replace("http:", "https:")

                            Picasso.with(requireContext()).load(url).into(binding.ivProfile)

                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.IMAGE,url)
                            SharedPreferenceUtils.getInstance(requireContext())!!
                                .setStringValue(ConstantUtils.USERNAME,response.body()!!.user_details.name)



                            binding.rvPropertypicture.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvPropertypicture.layoutManager =
                                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            propertyPictureAdapter = PropertyPictureAdapter(requireContext(), response.body()!!.user_details.virtual_pictures)

                            binding.rvPropertypicture.adapter = propertyPictureAdapter
                            (activity as HomeActivity?)?.update_user_details()

                        } else {
                            mAppLoader?.dismissDialog()
                            //  rlLoader!!.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()
                        //  rlLoader!!.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {
                    mAppLoader?.dismissDialog()
                    // rlLoader!!.visibility = View.GONE
                }

            }

            override fun onFailure(
                call: Call<PropertyPictureViewModel.EditProfileResponse>,
                t: Throwable
            ) {
                mAppLoader?.dismissDialog()
                // rlLoader!!.visibility = View.GONE
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()

            }

        })


    }

    fun updatedetails(
        name: String,
        date_of_birth: String,
        address: String,
        dimension_lawn: String,
        total_square_of_lawn: String,
        user_id: String

    ) {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        var stringStringHashMap = HashMap<String, String>()

        stringStringHashMap.put("name", name)
        stringStringHashMap.put("address", address)
        stringStringHashMap.put("date_of_birth", date_of_birth)
        stringStringHashMap.put("dimension_lawn", dimension_lawn)
        stringStringHashMap.put("total_square_of_lawn", total_square_of_lawn)
        stringStringHashMap.put(
            "user_id",
            SharedPreferenceUtils.getInstance(requireContext())
                .getStringValue(ConstantUtils.ID, "").toString(),
        )


        var userdetailsupdate = APIUtils.getServiceAPI()!!.updateprofile(stringStringHashMap)
        userdetailsupdate.enqueue(object :
            Callback<PropertyPictureViewModel.UpdateProfileResponse> {
            override fun onResponse(
                call: Call<PropertyPictureViewModel.UpdateProfileResponse>,
                response: Response<PropertyPictureViewModel.UpdateProfileResponse>
            ) {
                try {
                    if (response.code() == 200) {

                        if (response.body()!!.status == "1") {
                            mAppLoader?.dismissDialog()

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            var intent = Intent(requireContext(), HomeActivity::class.java)
                            startActivity(intent)

                        } else {
                            mAppLoader?.dismissDialog()

                            //rlLoader!!.visibility = View.GONE

                            Toast.makeText(
                                requireContext(),
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        mAppLoader?.dismissDialog()

                        // rlLoader!!.visibility = View.GONE

                        Toast.makeText(
                            requireContext(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: Exception) {

                    Toast.makeText(
                        requireContext(),
                        e.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    mAppLoader?.dismissDialog()

                    // rlLoader!!.visibility = View.GONE
                }

            }

            override fun onFailure(
                call: Call<PropertyPictureViewModel.UpdateProfileResponse>,
                t: Throwable
            ) {
                mAppLoader?.dismissDialog()

                //  rlLoader!!.visibility = View.GONE
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()

            }

        })

    }


    private fun userimageupdate() {
        mAppLoader?.showDialog()
        // rlLoader!!.visibility = View.VISIBLE

        val multiPartRepeatString = "imageUpdate"
        var image1: MultipartBody.Part? = null

        val userId: RequestBody = RequestBody.create(
            MultipartBody.FORM,
            user_id
        )

        if (!image1path.isNullOrEmpty()) {

            val file = File(image1path)
            val signPicBody =
                RequestBody.create(multiPartRepeatString.toMediaTypeOrNull(), file)
            image1 = MultipartBody.Part.createFormData("image", file.name, signPicBody)
        }


        var call = APIUtils.getServiceAPI()!!.userimageupdate(userId, image1)

        call.enqueue(object : Callback<PropertyPictureViewModel.UpdateProfileImageResponse> {
            override fun onResponse(
                call: Call<PropertyPictureViewModel.UpdateProfileImageResponse>,
                response: Response<PropertyPictureViewModel.UpdateProfileImageResponse>
            ) {
                try {

                    if (response.body()!!.status == "1") {

                        mAppLoader?.dismissDialog()
                        //  rlLoader!!.visibility = View.GONE
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


   /* private fun onClick() {

        binding.ivCamera.setOnClickListener {
            type = "camera"
            getPermissions()
        }
    }


    fun getPermissions() {
        if (PermissionUtils.checkPermission(
                requireContext() as Activity,
                Manifest.permission.CAMERA,
                REQUEST_CODE
            )
        ) {
            if (PermissionUtils.checkPermission(
                    requireContext() as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_CODE
                )
            ) {
                if (PermissionUtils.checkPermission(
                        requireContext() as Activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        REQUEST_CODE
                    )
                ) {
                    selectImage()
                }
            }
        }
    }

    private fun selectImage() {

        val items = arrayOf<CharSequence>("Take photo", "Choose from gallery")
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Add Photo")
        builder.setCancelable(true)
        builder.setItems(items) { dialog, item ->
            val result: Boolean = Utility.checkPermission(context)
            if (items[item] == "Take photo") {
                userChoosenTask = "Take photo"
                if (result) {
                    dispatchTakePictureIntent()

                }

            } else if (items[item] == "Choose from gallery") {
                userChoosenTask = "Choose from gallery"
                if (result) {
                    openGallery()
                }

            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    fun openGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )

        } else {
            if (Build.VERSION.SDK_INT <= 19) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_REQUEST
                )
            } else if (Build.VERSION.SDK_INT > 19) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_REQUEST
                )
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        val packageManager = requireActivity().packageManager
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".provider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image: File = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath()
        Log.d("photopath", currentPhotoPath)
        return image
    }

    fun onselectfromcamera1() {
        //  try {
        var bitmap: Bitmap? = null
        var imgFile = File(currentPhotoPath)

        //Toast.makeText(this,imgFile.toString(),Toast.LENGTH_LONG).show()
        Log.d("flow1", "dfdf")
        if (imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgFile = mCompressor!!.compressToFile(imgFile)
            bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            image1path = imgFile.path
            profileimage = getStringImage(bitmap)
            binding.ivProfile.setImageBitmap(bitmap)
            if (NetworkUtils.checkInternetConnection(requireContext())) {
                userimageupdate()
            }


        }
        Log.d("flow2", "dfdf")

    }

    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes: ByteArray = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

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
                    profileimage = bitmap?.let { getStringImage(it) }.toString()
                    civProfileImage!!.setImageBitmap(bitmap)
                    if (NetworkUtils.checkInternetConnection(requireContext())) {
                        userimageupdate()
                    }

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
        val imageCursor: Cursor? = requireActivity().contentResolver.query(
            uri,
            arrayOf(mediaPath),
            MediaStore.Images.Media._ID + "=" + documentID,
            null,
            null
        )
        if (imageCursor != null) {
            if (imageCursor.moveToFirst()) {
                result = imageCursor.getString(imageCursor.getColumnIndex(mediaPath))
            }
        }
        return result
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.data != null) {
            Log.e("filename", data.toString() + "")
            onselectfromgallery(data)
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            onselectfromcamera1()
        }
    }
*/







/*    private fun onClick() {

        binding.ivCamera!!.setOnClickListener {
            type = "camera"
            getPermissions()


        }
    }*/

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
        binding.ivProfile.setImageBitmap(bmImg)
        image1path=filePath
        userimageupdate()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    fun picturepropertylist() {
        picturelist = listOf(
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
        )
    }*/
}