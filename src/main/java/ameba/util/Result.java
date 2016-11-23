package ameba.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>Result class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class Result {
    private boolean success;
    private String code;
    private String message;
    private String description;
    private List<Error> errors;

    /**
     * <p>Constructor for Result.</p>
     *
     * @param success a boolean.
     */
    public Result(boolean success) {
        this.success = success;
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param errors  a {@link java.util.List} object.
     */
    public Result(String message, List<Error> errors) {
        this.success = false;
        this.message = message;
        this.errors = errors;
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param code a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     */
    public Result(String code, String message, String description, List<Error> errors) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.description = description;
        this.errors = errors;
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param code a int.
     * @param message a int object.
     * @param errors a {@link java.util.List} object.
     */
    public Result(int code, String message, List<Error> errors) {
        this(String.valueOf(code), message, errors);
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param code a int.
     * @param message a int object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     */
    public Result(int code, String message, String description, List<Error> errors) {
        this(String.valueOf(code), message, description, errors);
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param success a boolean.
     * @param message a {@link java.lang.String} object.
     */
    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param success a boolean.
     * @param code a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     */
    public Result(boolean success, String code, String message) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    /**
     * <p>Constructor for Result.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     */
    public Result(String message, String description, List<Error> errors) {
        this.success = false;
        this.message = message;
        this.description = description;
        this.errors = errors;
    }

    /**
     * <p>success.</p>
     *
     * @return a {@link ameba.util.Result} object.
     */
    public static Result success() {
        return new Result(true);
    }

    /**
     * <p>success.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result success(String message) {
        return new Result(true, message);
    }

    /**
     * <p>success.</p>
     *
     * @param code a int.
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result success(int code, String message) {
        return new Result(true, String.valueOf(code), message);
    }

    /**
     * <p>success.</p>
     *
     * @param code a String.
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result success(String code, String message) {
        return new Result(true, String.valueOf(code), message);
    }

    /**
     * <p>success.</p>
     *
     * @param code    a long.
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result success(long code, String message) {
        return new Result(true, String.valueOf(code), message);
    }

    /**
     * <p>failure.</p>
     *
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure() {
        return new Result(false);
    }

    /**
     * <p>failure.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String message) {
        return new Result(false, message);
    }

    /**
     * <p>failure.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String message, String description) {
        return new Result(message, description, null);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a int.
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(int code, String message) {
        return new Result(false, String.valueOf(code), message);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a long.
     * @param message a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(long code, String message) {
        return new Result(false, String.valueOf(code), message);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a int.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(int code, String message, String description) {
        return new Result(code, message, description, null);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String code, String message, String description) {
        return new Result(code, message, description, null);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a long.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(long code, String message, String description) {
        return new Result(String.valueOf(code), message, description, null);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a int.
     * @param message a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(int code, String message, List<Error> errors) {
        return new Result(code, message, errors);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a int.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(int code, String message, String description, List<Error> errors) {
        return new Result(code, message, description, errors);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String code, String message, String description, List<Error> errors) {
        return new Result(code, message, description, errors);
    }

    /**
     * <p>failure.</p>
     *
     * @param code a long.
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(long code, String message, String description, List<Error> errors) {
        return new Result(String.valueOf(code), message, description, errors);
    }

    /**
     * <p>failure.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String message, List<Error> errors) {
        return new Result(message, errors);
    }

    /**
     * <p>failure.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param errors a {@link java.util.List} object.
     * @return a {@link ameba.util.Result} object.
     */
    public static Result failure(String message, String description, List<Error> errors) {
        return new Result(message, description, errors);
    }

    /**
     * <p>isSuccess.</p>
     *
     * @return a boolean.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * <p>Setter for the field <code>success</code>.</p>
     *
     * @param success a boolean.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * <p>Getter for the field <code>code</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCode() {
        return code;
    }

    /**
     * <p>Setter for the field <code>code</code>.</p>
     *
     * @param code a {@link java.lang.String} object.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <p>Getter for the field <code>message</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>Setter for the field <code>message</code>.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description a {@link java.lang.String} object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Getter for the field <code>errors</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * <p>Setter for the field <code>errors</code>.</p>
     *
     * @param errors a {@link java.util.List} object.
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * <p>addError.</p>
     *
     * @param error a {@link ameba.util.Result.Error} object.
     */
    public void addError(Error error) {
        if (error == null) return;
        if (errors == null) {
            errors = Lists.newArrayList();
        }
        errors.add(error);
        success = false;
    }

    public static class Error {
        private String code;
        private String message;
        private String description;
        private String source;

        public Error(String message) {
            this.message = message;
        }

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public Error(String code, String message, String description) {
            this.code = code;
            this.message = message;
            this.description = description;
        }

        public Error(String code, String message, String description, String source) {
            this.code = code;
            this.message = message;
            this.description = description;
            this.source = source;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
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
}
