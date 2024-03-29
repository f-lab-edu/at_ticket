-- 테스트 데이터

INSERT INTO place
(address, name, phone_number, region)
VALUES('광화문로1', '서울 공연장1', '020001111', 'SEOUL');

INSERT INTO hall
(name, place_id)
VALUES('홀1', '1');

INSERT INTO hall
(name, place_id)
VALUES('홀2', '1');


INSERT INTO product
(age_limit, category, end_date, explains, image, inter_mission, name, running_time, start_date, sub_category, place_id)
VALUES('TWELVE', 'MUSICAL', '20240630', '공연설명', 'http://image.img', '00:10:00', '캣츠', '02:00:00', '20230531', 'ORIGINAL', '1');

INSERT INTO grade
(price, `type`, product_id)
VALUES('10000', 'A', '1');

INSERT INTO grade
(price, `type`, product_id)
VALUES('10000', 'B', '1');

INSERT INTO seat
(locx, locy,seat_row_num, seat_row, `space`, hall_id)
VALUES('15', '23', '1', 'h', "계단좌석", '1');

INSERT INTO product.seat
(locx, locy, seat_row_num, seat_row, `space`, hall_id)
VALUES('10', '1', '1', 'h', "2층좌석", '1');

INSERT INTO product.seat
(locx, locy, seat_row_num, seat_row, `space`, hall_id)
VALUES('10', '1', '1', 'h', "좌측좌석", '1');

INSERT INTO product.seat
(locx, locy, seat_row_num, seat_row, `space`, hall_id)
VALUES('10', '16', '21', 'h', "좌측좌석", '1');

INSERT INTO product.seat
(locx, locy, seat_row_num, seat_row, `space`, hall_id)
VALUES('2', '1', '1', 'h', "좌측좌석", '2');

INSERT INTO product.seat
(locx, locy, seat_row_num, seat_row, `space`, hall_id)
VALUES('4', '5', '1', 'h', "좌측좌석", '2');

INSERT INTO shows
(`date`, `session`, `time`, hall_id, product_id)
VALUES('20230531', 1, '10:00:00', '1', '1');

INSERT INTO product.shows
(`date`, `session`, `time`, hall_id, product_id)
VALUES('20230531', 1, '10:00:00', '2', '1');


INSERT INTO show_seat
(seats, grade_id, show_id)
VALUES('1,2', '1', '1');

INSERT INTO show_seat
(seats, grade_id, show_id)
VALUES('3', '2', '1');

INSERT INTO product.show_seat
(seats, grade_id, show_id)
VALUES('4,5', '1', '2');


INSERT INTO product.wish_product
(name, user_email,user_id,product_id)
VALUES('캣츠', 'qltkd8487@gmail.com',"user1",1);

INSERT INTO product.wish_product
(name, user_email,user_id,product_id)
VALUES('캣츠', 'gllgll23@naver.com',"user2",1);
