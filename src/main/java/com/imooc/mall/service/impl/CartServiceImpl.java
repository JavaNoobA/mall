package com.imooc.mall.service.impl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ProductStatusEnum;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.exception.ICartService;
import com.imooc.mall.form.CartForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.vo.CartProductVo;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 21:11
 */
@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    private Gson gson = new Gson();

    @Autowired(required = false)
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVo<CartVo> addCart(Integer uid, CartForm form) {
        Integer quantity = 1;

        Product product = productMapper.selectByPrimaryKey(form.getProductId());

        // 商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        // 商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        // 商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }

        // 写入到 redis, key: cart_1
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Cart cart = null;
        String value = hashOperator.get(cartKey, String.valueOf(uid));

        if (StringUtils.isEmpty(value)) {
            // 没有新增
            cart = new Cart(form.getProductId(), quantity, form.getSelected());
        } else {
            // 如果商品存在 数量需要累加
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        hashOperator.put(cartKey, String.valueOf(uid), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = hashOperator.entries(cartKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            int productId = Integer.parseInt(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);

            // TODO 需要优化
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVoList.add(cartProductVo);

                if (!cart.getProductSelected()) {
                    selectAll = false;
                }

                //计算总价(只计算选中的)
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
        }

        //有一个没有选中，就不叫全选
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, CartUpdateForm form, Integer productId) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Cart cart;
        String value = hashOperator.get(cartKey, String.valueOf(uid));

        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        } else {
            // 如果存在 修改属性
            cart = gson.fromJson(value, Cart.class);
            if (form.getQuantity() != null && form.getQuantity() > 0) {
                cart.setQuantity(form.getQuantity());
            }
            if (form.getSelected() != null) {
                cart.setProductSelected(form.getSelected());
            }
        }
        hashOperator.put(cartKey, String.valueOf(uid), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = hashOperator.get(cartKey, String.valueOf(uid));
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        hashOperator.delete(cartKey, String.valueOf(uid));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            hashOperator.put(cartKey, String.valueOf(uid), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            hashOperator.put(cartKey, String.valueOf(uid), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    private List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> hashOperator = redisTemplate.opsForHash();
        String cartKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = hashOperator.entries(cartKey);

        List<Cart> cartList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), Cart.class));
        }

        return cartList;
    }
}
