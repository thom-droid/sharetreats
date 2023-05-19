package lottery;

import item.Item;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 복권을 생성하고 관리하는 인터페이스입니다.
 * */
public interface LotteryInventory {

    Optional<Item> pick(LocalDateTime drawTime);

}
