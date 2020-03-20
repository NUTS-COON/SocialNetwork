package ru.nutscoon.sn.core.model.response;

public class BaseResponseWithResult<T> extends BaseResponse {
    private T result;
    private boolean success;
    private String error;

    public BaseResponseWithResult(T result) {
        this.result = result;
        this.success = true;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public void setError(String error) {
        this.error = error;
    }
}
