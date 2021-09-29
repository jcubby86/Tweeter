package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticatePresenter<RegisterPresenter.RegisterView> {

    public interface RegisterView extends AuthenticateView {}

    public RegisterPresenter(RegisterView view){
        super(view);
    }

    public void register(String firstName, String lastName, String userAlias, String password, ImageView imageToUpload) {
        // Convert image to byte array.
        Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        userService.register(firstName, lastName, userAlias, password, imageBytesBase64, getObserver());

    }

}
