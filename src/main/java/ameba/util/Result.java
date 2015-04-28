package ameba.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author icode
 */
public class Result {
    private boolean success;
    private Integer code;
    private String message;
    private String description;
    private List<Error> errors;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(String message, List<Error> errors) {
        this.success = false;
        this.message = message;
        this.errors = errors;
    }

    public Result(Integer code, String message, List<Error> errors) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public Result(Integer code, String message, String description, List<Error> errors) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.description = description;
        this.errors = errors;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, Integer code, String message) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public Result(String message, String description, List<Error> errors) {
        this.success = false;
        this.message = message;
        this.description = description;
        this.errors = errors;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result success(String message) {
        return new Result(true, message);
    }

    public static Result success(int code, String message) {
        return new Result(true, code, message);
    }

    public static Result failure() {
        return new Result(false);
    }

    public static Result failure(String message) {
        return new Result(false, message);
    }

    public static Result failure(String message, String description) {
        return new Result(message, description, null);
    }

    public static Result failure(int code, String message) {
        return new Result(false, code, message);
    }

    public static Result failure(int code, String message, String description) {
        return new Result(code, message, description, null);
    }

    public static Result failure(int code, String message, List<Error> errors) {
        return new Result(code, message, errors);
    }

    public static Result failure(int code, String message, String description, List<Error> errors) {
        return new Result(code, message, description, errors);
    }

    public static Result failure(String message, List<Error> errors) {
        return new Result(message, errors);
    }

    public static Result failure(String message, String description, List<Error> errors) {
        return new Result(message, description, errors);
    }

    public static class Error {
        private Integer code;
        private String message;
        private String description;
        private String source;

        public Error(String message) {
            this.message = message;
        }

        public Error(String source, String message) {
            this.message = message;
            this.source = source;
        }

        public Error(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Error(String source, String message, Integer code) {
            this.source = source;
            this.message = message;
            this.code = code;
        }

        public Error(Integer code, String message, String description) {
            this.code = code;
            this.message = message;
            this.description = description;
        }

        public Error(String message, String description, String source) {
            this.message = message;
            this.description = description;
            this.source = source;
        }

        public Error(Integer code, String message, String description, String source) {
            this.code = code;
            this.message = message;
            this.description = description;
            this.source = source;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void addError(Error error) {
        if (error == null) return;
        if (errors == null) {
            errors = Lists.newArrayList();
        }
        errors.add(error);
        success = false;
    }
}
