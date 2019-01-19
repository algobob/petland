package br.com.petland;

public enum ResponseStatus {
    OK("ok", 200),
    NOT_FOUND("not_found", 404),
    ERROR("error", 500),
    BAD_REQUEST("bad_request", 400);

    private String status;
    private int code;

    private ResponseStatus(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public String status() {
        return this.status;
    }
}