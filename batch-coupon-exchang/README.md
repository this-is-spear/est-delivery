
### 배치 실행 명령어

```shell
export PROD_DATASOURCE_URL=jdbc:mysql://{DB_HOST}:{DP_PORT}/batch?serverTimezone=Asia/Seoul
export PROD_DATASOURCE_USERNAME={USERNAME}
export PROD_DATASOURCE_PASSWORD={PASSWORD}

java -jar batch-coupon-exchang/build/libs/batch-coupon-exchang-1.0-SNAPSHOT.jar  \
--spring.profiles.active=prod \
name='100% 할인 보상 쿠폰' \
description='배송 오류로 인한 쿠폰 교환' \
amountType='RATE' \
amount=100 \
expiredCouponId=13
```

