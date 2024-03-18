# java-blackjack

# 블랙잭 1단계 미션 - 블랙잭 게임 실행

## 1. 기능 요구사항

- 블랙잭 게임을 변형한 프로그램을 구현한다. 블랙잭 게임은 딜러와 플레이어 중 카드의 합이 21 또는 21에 가장 가까운 숫자를 가지는 쪽이 이기는 게임이다.

- 카드의 숫자 계산은 카드 숫자를 기본으로 한다.
  - 예외로 Ace는 1 또는 11로 계산할 수 있다.
  - King, Queen, Jack은 각각 10으로 계산한다.

- 게임을 시작하면 플레이어는 두 장의 카드를 지급 받는다.
  - 두 장의 카드 숫자를 합쳐 21을 초과하지 않으면서 21에 가깝게 만들면 이긴다.
  - 21을 넘지 않을 경우 원한다면 얼마든지 카드를 계속 뽑을 수 있다.
  - 21을 초과한다면 게임에서 패한다.
- 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 하고, 17점 이상이면 추가로 받을 수 없다.
- 게임을 완료한 후 각 플레이어별로 승패를 출력한다.

- **블랙잭 용어 정리**
> 핸드 (Hand) - 참가자가 가지고 있는 카드의 총합
> 스탠드(Stand) - 카드 추가 수령을 멈추는 것.
> 히트 (Hit) - 카드 추가 수령을 진행하는 것.
> 버스트 (Bust) - 핸드가 21을 초과하는 것. 이 경우 패하는 것으로 간주하며, 히트가 불가능하다.
> 
> 소프트 핸드 (Soft Hand) - Ace 카드를 11로 간주하여 게임을 진행하는 것.
> 하드 핸드 (Hard Hand) - 카드 추가 수령 후, Ace 카드를 11에서 1로 변경하여 게임을 진행하는 것. 버스트가 된 경우에 자동으로 적용된다.


## 2. 기능 목록

### 2-0. 용어 정리
- **플레이어**: 게임에 참가하는 사람의 한 종류. 카드 수령 여부를 결정 할 수 있다.
- **딜러**: 게임에 참가하는 사람 종류. 카드 수령 여부를 결정 할 수 없다.
- **참가자**: 플레이어와 딜러를 포괄하는 게임 참가하는 모든 사람.

### 2-1. 입력

1. [x] 플레이어 이름 입력 받는다.
   - [x] 1명 이상의 플레이어 이름이 입력되어야 한다.
   - [x] `,` 쉼표를 기준으로 입력된 다수의 플레이어 입력 받는다.
   - [x] 공백을 제외한 모든 문자를 플레이어 이름 입력값으로 허용한다. 
       - [x] 단, 이름 앞뒤에 존재하는 공백은 허용한다. 
   - [x] 중복된 플레이어 이름의 입력은 허용하지 않는다.

2. [x] 플레이어별 배팅 금액을 입력 받는다.
   - [x] 양의 정수만 입력 가능하다.

3. [x] 게임 진행 중, 카드 추가 수령 여부를 입력 받는다.
   - [x] `y` 혹은 `n` 으로 입력 받는다.
     - [x] 대소문자 구분 없이 입력 받을 수 있다.

### 2-2. 메인 로직 실행

1. [x] 카드를 중복되지 않게 임의로 선택하여 참가자들에게 분배한다.
    - [x] 게임 시작 후 첫 분배에는 각각 참가자들에게 2장씩 분배한다.
    - [x] 2 번째 분배부터는 카드 수령 여부를 입력 받아 임의로 분배 받는다.
      - [x] 딜러는 모든 플레이어가 추가 카드 수령을 완료 한 후 카드 1장을 분배 받는다.
        - [ x 첫 분배에 받은 2장의 카드가 16이하이면 무조건 추가로 1장을 분배 받는다.
          - [x] 딜러는 추가 1장의 카드를 분배 받은 이후, 핸드가 17이상이 될 때까지 카드를 받는다.

2. [x] 모든 참가자들의 카드 분배가 완료 된 후 결과를 계산한다.
    - [x] 개별 참가자들이 소유하고 있는 카드의 총합을 계산한다.
      - [x] 모든 참가자(딜러와 플레이어(들))는 첫 분배에서 받은 `Ace` 카드의 숫자를 11을 기본으로 가진다.
      - [x] 추가로 분배 받은 핸드가 21을 초과하면 `Ace` 카드의 숫자를 1로 취급하여 계산한다.

### 2-3. 출력

1. [x] 사용자 입력 요구 프롬프트 출력

2. [x] 카드 지급 결과 출력
   - [x] 전체 지급 결과 출력
   - [x] 개별 지급 결과 출력
     - [x] 딜러는 첫 분배 직후, 1장의 카드만 공개한다.
     - [x] 플레이어는 첫 분배 직후, 2장의 카드를 모두 공개한다.
     - [x] 플레이어는 히트한 카드를 모든 플레이어의 히트가 완료된 후 전부 공개한다.
   - [x] 최종 지급 계산 결과 출력

3. [x] 최종 수익 결과 출력
   - [x] 딜러의 최종 수익 출력
   - [x] 개별 플레이어들의 최종 수익 출력

## 2. 실행 결과

```
게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)
pobi,jason

pobi의 배팅 금액은?
10000

jason의 배팅 금액은?
20000

딜러와 pobi, jason에게 2장을 나누었습니다.
딜러: 3다이아몬드
pobi카드: 2하트, 8스페이드
jason카드: 7클로버, K스페이드

pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
y
pobi카드: 2하트, 8스페이드, A클로버
pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
n
pobi카드: 2하트, 8스페이드, A클로버
jason은 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
n
jason카드: 7클로버, K스페이드

딜러는 16이하라 한장의 카드를 더 받았습니다.

딜러 카드: 3다이아몬드, 9클로버, 8다이아몬드 - 결과: 20
pobi카드: 2하트, 8스페이드, A클로버 - 결과: 21
jason카드: 7클로버, K스페이드 - 결과: 17

## 최종 수익
딜러: 10000
pobi: 10000 
jason: -20000

```

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)
