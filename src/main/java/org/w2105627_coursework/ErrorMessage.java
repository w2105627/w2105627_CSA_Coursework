package org.w2105627_coursework;

public class ErrorMessage
{
    private String error;
    private int errorCode;
    private String errorMessage;

    public ErrorMessage(){}

    public ErrorMessage(String error, int errorCode, String errorMessage){
        this.error = error; this.errorCode = errorCode; this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
