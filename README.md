# WarGame

방법:
공격카드 :  #만큼 카드 내는 동안에 공격카드가 나와야 함. 공격카드가 나오면 방어에 성공한 것이고, 안나오면 공격카드를 낸 사람이 전부 가져간다.
A: 3
2: 2
k: 3
Q: 2
J: 1

1. 카드 덱 섞기
2. 2명의 플레이어에게 카드 나눠주기
3. 카드 뒤집어서 집은 상태에서, 위에서부터 1장씩 바닥에 쌓음
4. 돌아가면서 한장씩 내다가 한 플레이어가 '공격카드'를 내게 되는경우 다음 플레이어는 공격카드가 나올때까지 카드를 내야함
(예를 들어 p1이 A를 냈으면, p2는 3회내에 공격카드가 나와야함.)
(+공격카드가 나와서 방어에 성공하면, 이번엔 상대방이 그 공격카드 값만큼 카드를 내서 방어를 해야함. -> 예를 들어, p1이 k를 내서 p2가 방어를 해야함. 그결과 p2가 4,q가 나왔다. 그러면 q로 방어 성공했고 q는 공격카드이므로 p1이 방어해야함. 즉, p1이 2회내로 공격카드가 나와야 함. 안나오면 p2가 승리)
5. 이긴사람이 바닥에 쌓아 놓은 카드더미를 가져감
6. 4~5 반복해서 모든 카드를 가져가는 사람이 최종 승리.

+카드를 내다가 연속으로 같은 번호의 카드가 나오면 먼저 카드더미를 치는사람이 가져감(공격카드든 일반카드든 상관없음)
(예를 들어 P1이 10을 냈는데 p2가 10을 냄. 그러면 먼저 치는 사람이 그 더미 가져감)
