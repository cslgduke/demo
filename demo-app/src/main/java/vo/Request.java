package vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author i565244
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private String apId;
}
