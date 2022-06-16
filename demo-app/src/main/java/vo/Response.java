package vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author i565244
 */
@Data
@Builder
public class Response<R>{
    private String code;

    private R data;
}