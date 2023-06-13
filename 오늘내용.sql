-- People 테이블에 데이터 인서트

use yhdb;

insert into people
(name, birthdate, birthtime, birthdt)
values
( 'Mike', '1990-11-11', '10:07:35', '1990-11-11 10:07:35'),
( 'Larry', '1972-12-25', '04:10:42', '1972-12-25 04:10:42');

select * from people;

-- 날짜 정보만 가져오기 
select name, day( birthdate )
from people;

select name, dayname( birthdate )
from people;

select name, dayofweek( birthdate  )
from people;

select name, dayofyear( birthdate  )
from people;

select name, month( birthdate  ), birthdate
from people;

select name, birthtime, hour(birthtime), 
		minute(birthtime), second(birthdt)
from people;

-- db 에 저장된 시간형식의 데이터를 
-- 사람이 보기 편한 데이터로 바꾸는 방법 

-- 년월일시 
select name, birthdt, date_format(birthdt, '%Y년 %m월 %d일, %h시')
from people;

-- 현재 시간을 가져오고 싶을때, now() 함수를 사용. 
select now();

-- 현재 년월일 만 가져오고 싶을때, curdate()
select curdate();

-- 현재 시분초만 가져오고 싶을때, curtime()
select curtime();

-- 시간의 차이를 구하는 방법 : datediff() 함수 
-- birthdt 시간과, 현재시간의 차이를 구하자. 
select datediff( now() ,  birthdt )
from people;

select birthdt, date_add(birthdt, interval 100 day)
from people;

select birthdt, date_add(birthdt, interval 5 week)
from people;

select birthdt, date_add(birthdt, interval 5 hour)
from people;

select birthdt, date_sub(birthdt, interval 5 hour)
from people;

select birthdt, birthdt + interval 100 day
from people;

select birthdt, birthdt - interval 100 day
from people;

select birthdt, birthdt + interval 2 month + interval 21 day + interval 3 hour
from people;

-- Comments 테이블 생성 
-- comments
-- - id
-- - content : 최대 200글자
-- - createdAt : timestamp

insert into comments 
( content, createdAt )
values
( '좋아요' , now() );

select * from comments;

insert into comments 
( content, createdAt )
values
( '저는 싫어요' , now() );


select * from comments;

insert into comments 
( content )
values
( '새로운 댓글입니다.'  );

-- 댓글 수정 
update comments
set content = '댓글수정~'
where id = 6;

-- 회원 
-- id  
-- 이메일
-- 이름
-- 전화번호
-- 집주소
-- 성별
-- 나이

-- 제품
-- id
-- 상품명
-- 가격

-- 주문
-- 제품 id
-- 주문한사람의 id
-- 주문날짜 
-- 주문 수량

insert into orders
( order_date, amount, customer_id)
values
( curdate(), 10.5, 3);

-- 데이터 인서트.

-- 데이터 확인.
select count(*) from customers;
select count(*) from orders;

-- 두개 테이블을, 하나로 합쳐서 가져오시오. 
select *
from orders
join customers
on orders.customer_id = customers.id ;

-- 테이블의 이름을 줄여서 사용하는 방법 
select *
from orders as o
join customers as c
on o.customer_id = c.id ;

-- 중복된 컬럼이름을 변경해야 한다. 
select o.id as order_id, order_date , amount,
		o.created_at , c.id as customer_id, 
        first_name, last_name, email
from orders as o
join customers as c
on o.customer_id = c.id ;

-- 위의 조인은!!! 
-- 두 개 테이블에, 공통으로 들어있는 데이터만 
-- 합쳐서 가져온 것이다. 

-- 예를들어서, 회원가입은 했는데, 주문을 한번도 안한사람은
-- 위의 조인에는 없다!!! 

-- 모든 고객 데이터를 가져오되, 
-- 주문 정보가 없는 고객도 나타나도록 가져오는 방법 
select *
from customers c
left join orders o
on c.id = o.customer_id;

select *
from orders o
right join customers c
on c.id = o.customer_id;

-- 주문 금액이 600달러보다 큰 데이터를 가져오시오.
-- 그사람의 이메일 주소와 이름을 확인할 수 있어야 합니다.

select *
from orders o
join customers c
	on o.customer_id = c.id
where amount > 600;

-- 주문 금액이 600달러보다 큰 사람의 이메일과 주문금액, 
-- 주문날짜를 가져오시오 .
select email, amount, o.created_at
from orders o
join customers c
	on o.customer_id = c.id
where amount > 600;

-- 위의 결과를, 주문날짜 내림차순으로 가져오시오.
select email, amount, o.created_at
from orders o
join customers c
	on o.customer_id = c.id
where amount > 600
order by o.created_at desc;

-- 고객 아이디가 36인 사람의 주문내역을 가져오시오. 
select *
from customers c
join orders o
	on c.id = o.customer_id
where c.id = 36;

-- 퍼스트 네임이 'Cobby' 인 사람의 주문내역을 가져오시오.
select * 
from customers c
join orders o
	on c.id = o.customer_id
where first_name = 'Cobby';

-- 퍼스트네임에 ty 가 들어가는 사람의 주문내역을 가져오시오.
select *
from customers c
join orders o
	on c.id = o.customer_id
where first_name like '%ty%' ;

-- 주문금액이 300 이상이고 500 이하인, 주문내역을 가져오시오.
-- 단, 주문한 사람의 이메일정보도 같이 나와야 합니다.
select *
from orders o
join customers c
	on o.customer_id = c.id
where amount >= 300 and amount <= 500;

select *
from orders o
join customers c
	on o.customer_id = c.id
where amount between 300 and 500 ;

-- 각 고객별로, 주문 수를 나타내시오. 
select c.id as customer_id, 
		c.first_name, c.last_name, 
        c.email, count(o.id) as order_cnt
from customers c
left join orders o
	on c.id = o.customer_id
group by c.id;


-- 각 고객별로 주문 금액 평균이 
-- 300 달러 이상인 데이터만 가져오시오.
select c.id, c.email , avg(amount) as avg_amount
from customers c
left join orders o 
	on c.id = o.customer_id
group by c.id having avg_amount >= 300 ;

-- 각 고객별로 주문 금액 최대값을 구하고, 
-- 이 값이 600 달러 이상인 데이터만 가져와서, 
-- 내림차순으로 정렬하시오.
select c.id, c.first_name,
		c.last_name, c.email, 
        max(amount) as max_amount
from customers c
left join orders o
	on c.id = o.customer_id
group by c.id  having max_amount >= 600
order by max_amount desc ;

-- 주문날짜의 최소값과 최대값을 확인하시오.
select min(order_date), max(order_date)
from orders;

-- 2019년 12월 20일부터 2020년 1월 10일 사이에 
-- 주문한 사람은 몇명입니까?
select  count( distinct customer_id )
from orders 
where order_date >= '2019-12-20' and order_date <= '2020-01-10';

-- 2019년 12월 20일부터 2020년 1월 10일 사이의 
-- 주문 데이터에서, 
-- 고객별 주문금액 평균이 300달러 이상인 사람의 
-- 이름과, 평균금액을 가져오시오. 
select c.first_name, c.last_name , 
		avg( amount ) as avg_amount
from orders o
join customers c
	on o.customer_id = c.id
where order_date >= '2019-12-20' and 
		order_date <= '2020-01-10'
group by o.customer_id having avg_amount >= 300
order by avg_amount desc;

-- students / papers

select first_name, title, grade
from papers p 
join students s
	on p.student_id = s.id
order by grade desc;


select first_name, title, grade
from students s
left join papers p
	on s.id = p.student_id
order by s.id asc, grade asc;

-- NULL 인 값을 다른값으로 바꾸는 함수 ifnull()

select first_name, 
	   ifnull(title, 'MISSING') as title, 
       ifnull(grade, 0 ) as grade
from students s
left join papers p
	on s.id = p.student_id
order by s.id asc, grade asc;


select first_name, ifnull( avg(grade) ,  0) as average
from students s 
left join papers p 
	on s.id = p.student_id
group by s.id
order by average desc;


select first_name, 
		ifnull( avg(grade) ,  0) as average,
        if( avg(grade) >= 75, 'PASSING', 'FAILING' ) as passing_status
from students s 
left join papers p 
	on s.id = p.student_id
group by s.id
order by average desc;


-- TV Series 

select * from reviewers;
select * from series;
select * from reviews;