package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticatePresenterObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {

    private final UserService userService;
    private final RegisterView view;

    public interface RegisterView extends AuthenticatePresenterObserver {}

    public RegisterPresenter(RegisterView view){
        this.view = view;
        userService = new UserService();
    }

    public void register(String firstName, String lastName, String userAlias, String password, ImageView imageToUpload) {
        // Convert image to byte array.
        Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        userService.register(firstName, lastName, userAlias, password, imageBytesBase64, new UserService.RegisterObserver(){
            @Override
            public void handleSuccess(User registeredUser) {
                view.authenticateUser(registeredUser);
            }
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }
        });

    }

}
