package com.example.administrator.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.MyUser;
import com.example.administrator.smartbutler.ui.CourierActivity;
import com.example.administrator.smartbutler.ui.LoginActivity;
import com.example.administrator.smartbutler.ui.PhoneActivity;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.UtilsTools;
import com.example.administrator.smartbutler.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;
    private CustomDialog dialog;

    private TextView edit_data;
    private EditText et_username;
    private EditText et_ages;
    private EditText et_sex;
    private EditText et_descs;
    private CircleImageView profile_image;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    private Button update_ok;

    private TextView courier_check;
    private TextView phone_query;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);

        findView(view);
        return view;
    }

    private void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);

        edit_data = (TextView) view.findViewById(R.id.edit_data);
        edit_data.setOnClickListener(this);

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_ages = (EditText) view.findViewById(R.id.et_ages);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_descs = (EditText) view.findViewById(R.id.et_descs);

        update_ok = (Button) view.findViewById(R.id.update_ok);
        update_ok.setOnClickListener(this);

        courier_check = (TextView) view.findViewById(R.id.courier_check);
        courier_check.setOnClickListener(this);

        phone_query = (TextView) view.findViewById(R.id.phone_query);
        phone_query.setOnClickListener(this);


        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //头像的获取
        UtilsTools.getImageToShare(getActivity(), profile_image);


        //自定义的Dialog
        dialog = new CustomDialog(getActivity(), 1000, 700, R.layout.dialog_photo,
                R.style.Theme_dialog, Gravity.BOTTOM, 0);
        //屏幕外点击无效
        dialog.setCancelable(false);


        //添加照片
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认是不可点击或者修改
        setEnable(false);

        //获取当前用户的信息
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userInfo.getUsername());
        et_ages.setText(userInfo.getAge() + ""); //setText默认的是String类型数据
        et_sex.setText(userInfo.getSex() ? "男" : "女");
        et_descs.setText(userInfo.getDesc());

    }

    //方框是否可以点击修改
    private void setEnable(Boolean is) {
        et_username.setEnabled(is);
        et_ages.setEnabled(is);
        et_sex.setEnabled(is);
        et_descs.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录界面
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_data:
                //显示"确认修改"的按钮
                update_ok.setVisibility(View.VISIBLE);
                //方框可以编辑
                setEnable(true);
                break;
            case R.id.update_ok:
                //1.拿到数据框的内容
                String userName = et_username.getText().toString().trim();
                String age = et_ages.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String desc = et_descs.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(age)
                        && !TextUtils.isEmpty(sex)
                        && !TextUtils.isEmpty(desc)) {
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(userName);
                    user.setAge(Integer.parseInt(age));
                    user.setDesc(desc);
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //关闭"确认修改"的按钮
                                update_ok.setVisibility(View.GONE);
                                setEnable(false);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.profile_image:
                dialog.show();
                break;

            case R.id.btn_camera:
                toCamera();
                break;

            case R.id.btn_picture:
                toPicture();
                break;

            case R.id.btn_cancel:
                dialog.dismiss();
                break;

            case R.id.courier_check:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;

            case R.id.phone_query:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

    public final static String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public final static int CAMERA_REQUEST_CODE = 100;
    public final static int PHOTO_REQUEST_CODE = 101;
    private final static int RESULT_REQUEST_CODE = 102;
    private Uri imageUri;

    //照相机的File对象
    private File mCameraFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相机
    private void toCamera() {

        //启动相机程序
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //判断系统的等级
        if (Build.VERSION.SDK_INT >= 24) {//安卓7.0
            imageUri = FileProvider.getUriForFile(getActivity(),
                    "com.example.smartbulter.fileprovider", mCameraFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE);

        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:
                    //获取到图片的路径
//                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    if (Build.VERSION.SDK_INT >= 24) {
                        startPhotoZoom(FileProvider.getUriForFile(getActivity(),
                                "com.example.smartbulter.fileprovider", mCameraFile));
                    } else {
                        startPhotoZoom(Uri.fromFile(mCameraFile));
                    }
                    break;

                //相册数据
                case PHOTO_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能舍弃
                    if (data != null) {
                        setImageToView(data);
                        //将原来的照片进行删除
                        if (mCameraFile != null) {
                            mCameraFile.delete();
                        }
                    }

                    break;
            }
        }
    }

    //裁剪图片
    private void startPhotoZoom(Uri uri) {
        if (uri == null) { //判断路径是否为空
            L.e("Uri = null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");

        if (Build.VERSION.SDK_INT >= 24) { //安卓7.0的系统

            intent.setDataAndType(uri, "image/*");
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        //发送数据
        intent.putExtra("return-data", true);

        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    public void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //保存头像照片
        UtilsTools.putImageToShare(getActivity(), profile_image);
    }
}
