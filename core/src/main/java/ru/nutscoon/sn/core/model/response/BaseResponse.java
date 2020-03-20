package ru.nutscoon.sn.core.model.response;

public class BaseResponse {

    private boolean success;
    private String error;


    public BaseResponse() {

    }

    public BaseResponse(boolean success) {
        this.success = success;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T extends BaseResponse> T createError(Class<T> tClass, String error) {
        T response;

        try {
            response = tClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setError(error);
        response.setSuccess(false);

        return response;
    }
}
