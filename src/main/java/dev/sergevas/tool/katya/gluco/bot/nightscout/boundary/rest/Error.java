package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.Objects;
import java.util.StringJoiner;


public class Error {

    private Integer code;
    private String message;
    private Object fields;

    public Error() {
    }

    /**
     *
     **/
    public Error code(Integer code) {
        this.code = code;
        return this;
    }

    @JsonbProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonbProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     **/
    public Error message(String message) {
        this.message = message;
        return this;
    }

    @JsonbProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonbProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     **/
    public Error fields(Object fields) {
        this.fields = fields;
        return this;
    }

    @JsonbProperty("fields")
    public Object getFields() {
        return fields;
    }

    @JsonbProperty("fields")
    public void setFields(Object fields) {
        this.fields = fields;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Error error = (Error) o;
        return Objects.equals(this.code, error.code) &&
                Objects.equals(this.message, error.message) &&
                Objects.equals(this.fields, error.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, fields);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Error.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("fields=" + fields)
                .toString();
    }
}
