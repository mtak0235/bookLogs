package hello.itemservice.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;

@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {
	private final EntityManager em;
	private final JPAQueryFactory query;

	public JpaItemRepositoryV3(EntityManager em) {
		this.em = em;
		this.query = new JPAQueryFactory(em);
	}

	@Override
	public Item save(Item item) {
		em.persist(item);
		return item;
	}

	@Override
	public void update(Long itemId, ItemUpdateDto updateParam) {
		Item findItem = em.find(Item.class, itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}

	@Override
	public Optional<Item> findById(Long id) {
		Item item = em.find(Item.class, id);
		return Optional.ofNullable(item);
	}

	@Override
	public List<Item> findAll(ItemSearchCond cond) {
		String itemName = cond.getItemName();
		Integer maxPrice = cond.getMaxPrice();

		List<Item> result = query
			.select(QItem.item)
			.from(QItem.item)
			.where(likeItemName(itemName), maxPrice(maxPrice))
			.fetch();
		return result;
	}

	private BooleanExpression likeItemName(String itemName) {
		if (StringUtils.hasText(itemName)) {
			return QItem.item.itemName.like("%" + itemName + "%");
		}
		return null;
	}

	private BooleanExpression maxPrice(Integer maxPrice) {
		if (maxPrice != null) {
			return QItem.item.price.loe(maxPrice);
		}
		return null;
	}

	public List<Item> findAllOld(ItemSearchCond cond) {
		String itemName = cond.getItemName();
		Integer maxPrice = cond.getMaxPrice();
		BooleanBuilder builder = new BooleanBuilder();
		if (StringUtils.hasText(itemName)) {
			builder.and(QItem.item.itemName.like("%" + itemName + "%"));
		}
		if (maxPrice != null) {
			builder.and(QItem.item.price.loe(maxPrice));
		}

		List<Item> result = query
			.select(QItem.item)
			.from(QItem.item)
			.where(builder)
			.fetch();
		return result;
	}
}
