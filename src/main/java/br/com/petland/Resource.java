package br.com.petland;

import java.util.List;
import static java.util.Arrays.asList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.petland.pet.Pet;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import spark.Response;

@EqualsAndHashCode
@ToString
public class Resource<T> {
    private String status;
    private int code;
    private List<String> messages;
    private T data;

    private Resource(String status, int code, T data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

	public Resource(String status, int code, List<String> messages, T data) {
        this(status, code, data);
        this.messages = messages;
    }

    public Resource(String status, int code, List<String> messages) {
        this.status = status;
        this.code = code;
        this.messages = messages;
    }

    public static <T extends Object> Resource<T> success(T data) {
		return new Resource<T>(ResponseStatus.OK.status(), ResponseStatus.OK.code(), data);
    }

    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();  
        gsonBuilder.serializeNulls();  
        return gsonBuilder.create().toJson(this);
    }

	public static <T extends Object> Resource<T> notFound(T data, String message) {
		return new Resource<T>(ResponseStatus.NOT_FOUND.status(), ResponseStatus.NOT_FOUND.code(), asList(message), data);
	}

	public static <T extends Object> Resource<T> error(String message) {
		return new Resource<T>(ResponseStatus.ERROR.status(), ResponseStatus.ERROR.code(), asList(message));
	}

	public static <T extends Object> Resource<T> badRequest(String message) {
		return new Resource<T>(ResponseStatus.BAD_REQUEST.status(), ResponseStatus.BAD_REQUEST.code(), asList(message));
	}
}