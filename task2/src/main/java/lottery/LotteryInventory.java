package lottery;

import item.Item;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LotteryInventory {

    Optional<Item> pick(LocalDateTime drawTime);

}
