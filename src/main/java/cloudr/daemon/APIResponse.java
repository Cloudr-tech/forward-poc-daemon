package cloudr.daemon;

public class APIResponse {
    private boolean status;
    private String message;
    private Object data;

    public boolean getStatus() { return this.status; }

    public String getMessage() { return this.message; }

    public Object getData() { return this.data; }
}
