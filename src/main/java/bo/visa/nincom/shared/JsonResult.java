package bo.visa.nincom.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {
    private Boolean success;
    private Object data;
    private String message;

    public JsonResult(boolean b, Object o, String message) {
        this.success = b;
        this.data = o;
        this.message = message;
    }
}