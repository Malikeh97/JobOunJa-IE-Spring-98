package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidInfo {
    private String biddingUser;
    private String projectTitle;
    private Integer bidAmount;
}
