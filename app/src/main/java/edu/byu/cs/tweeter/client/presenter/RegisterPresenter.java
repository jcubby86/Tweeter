package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.BackgroundTaskObserver;
import edu.byu.cs.tweeter.client.presenter.observers.AuthenticateView;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterPresenter extends Presenter<AuthenticateView> {

    public RegisterPresenter(AuthenticateView view) {
        super(view);
    }

    public void register(String firstName, String lastName, String userAlias, String password, ImageView imageToUpload) {
        // Convert image to byte array.
        Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();
        String imageBytesBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        RegisterRequest registerRequest = new RegisterRequest(userAlias, password, firstName, lastName, imageBytesBase64);

        getUserService().register(registerRequest, new BackgroundTaskObserver<RegisterResponse>() {
            @Override
            public void handleFailure(String message) {
                view.displayErrorMessage(message);
            }

            @Override
            public void handleSuccess(RegisterResponse response) {
                Cache.getInstance().setCurrUserAuthToken(response.getAuthToken());
                Cache.getInstance().setCurrUser(response.getUser());

                view.authenticateUser(response.getUser());
            }
        });

    }

}
