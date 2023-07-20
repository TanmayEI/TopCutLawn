package com.topcutlawn.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {


    public static boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private static final String PHONE_REGEX = "\\d{8,10}";


    public static String checkEmptyOrNull(String str)
    {
        if (str == null || str.length() == 0)
            return str;
        else
            return str="";
    }

    public static String removeSpecialCharacter(String str)
    {

        if (str.endsWith("%")) {
            StringBuffer number = new StringBuffer(str).deleteCharAt(str.length() - 1);
            float f = Float.valueOf(String.valueOf(number));

            return String.valueOf(number);
        } else
        {
            return str="0";
        }

//        try
//        {
//            str = str.replaceAll("[^a-zA-Z0-9]", "");
//            if (str != null && str.length() > 0)
//                return str;
//            else
//                return str="0";
//        }
//        catch (Exception exception)
//        {
//            return str="0";
//        }


    }


    public static void loadFromHTML(TextView textView, String text)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text,  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            textView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }
    }

    public static boolean isValidPhoneNumber(CharSequence target) {
        if (target.length() < 10) {
            return false;
        } else {

            if (target.length()==10)
            {
                return  true;
            }
            else
            {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }

        }
    }

    public static boolean checkNoNZeroNumber(String target) {
        int infoINt = Integer.parseInt(target);
        if(infoINt == 0){
            return true;
        }else{
            return false;
        }


    }



    public static Spannable getColoredString(String mString, int colorId) {
          // how to use
       // textView.append(TextViewUtils.getColoredString(getString(R.string.preString),ContextCompat.getColor(getActivity(),R.color.firstColor)));

        Spannable spannable = new SpannableString(mString);
        spannable.setSpan(new ForegroundColorSpan(colorId), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static void loadImageWithGlide(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                . diskCacheStrategy(DiskCacheStrategy.ALL)
               // .placeholder(R.drawable.user) //placeholder
                //.error(R.drawable.ic_sport_mw) //error
                .into(imageView);
    }


    public static void  loadImageWithProgressbar(Context context, ImageView imageView, final ProgressBar progressBar, String url)
    {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                . diskCacheStrategy(DiskCacheStrategy.ALL)
               // .placeholder(R.drawable.user) //placeholder
              //  .error(R.drawable.app_icon) //error
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }


                })
                .into(imageView);
    }
}
