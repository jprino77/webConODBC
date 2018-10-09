USE `db_los_amigos`;
DROP procedure IF EXISTS `TRAER_CANCHAS_DISPONIBLES`;

DELIMITER $$
USE `db_los_amigos`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `TRAER_CANCHAS_DISPONIBLES`(
	IN p_filial_id int,
	IN p_deporte int,
    IN p_fecha_desde timestamp,
    IN p_fecha_hasta timestamp
)
    COMMENT 'Trae las canchas disponibles entre determinadas fechas'
BEGIN
 select distinct ca.id as 'cancha_id', ca.codigo as 'cancha_codigo', tc.id as 'tipo_id', tc.descripcion as 'tipo_descripcion' 
 from cancha ca 
 inner join tipo_cancha tc on tc.id= ca.tipo_cancha_id
 where ca.filial_id = p_filial_id and ca.deporte_id = p_deporte
 and ca.id not in (select cancha_id from  turno tu
 where  ( p_fecha_desde between tu.fecha_hora_desde and tu.fecha_hora_hasta)
 or ( p_fecha_hasta between tu.fecha_hora_desde and tu.fecha_hora_hasta)
 or (tu.fecha_hora_desde between p_fecha_desde and p_fecha_hasta)
 or (tu.fecha_hora_hasta between p_fecha_desde and p_fecha_hasta) 
 and tu.cancelado = 0)
 order by ca.codigo;
END$$

DELIMITER ;
USE `db_los_amigos`;
DROP procedure IF EXISTS `TRAER_ALQUILERES_USUARIO`;

DELIMITER $$
USE `db_los_amigos`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `TRAER_ALQUILERES_USUARIO`(IN p_usuario_id int, IN p_filial_id int, IN p_deporte_id int,IN p_fecha_alquiler_desde date, IN p_fecha_alquiler_hasta date)
    COMMENT 'Trae las canchas alquiladas por el usuario'
BEGIN

set @usuario = concat(' where tu.usuario_id = ',QUOTE(p_usuario_id), ' and tu.cancelado = 0 ');

	IF p_filial_id is not NULL THEN
		set @filial = CONCAT (' and ca.filial_id = ', QUOTE(p_filial_id));
	ELSE
		set @filial = ' ';
	END IF;

	IF p_deporte_id is not NULL THEN
		set @deporte = CONCAT (' and de.id = ', QUOTE(p_deporte_id));
	ELSE
		set @deporte = ' ';
	END IF;

	IF (p_fecha_alquiler_desde is not NULL) THEN
		set @fecha_alquiler = CONCAT (' and CAST(tu.fecha_hora_desde AS DATE) between ', QUOTE(p_fecha_alquiler_desde), ' and ', QUOTE(p_fecha_alquiler_hasta));
	ELSE
		set @fecha_alquiler = ' ';
	END IF;


set @querys = CONCAT ('select ca.codigo as cancha_codigo, tc.descripcion as tipo_cancha_descripcion, de.descripcion as deporte_descripcion, tu.fecha_hora_desde as fecha_hora_desde, tu.fecha_hora_hasta as fecha_hora_hasta , 
case 
 when TIMESTAMPDIFF(minute,now(),tu.fecha_hora_desde) > 120 then true 
 else false end as puede_anular,tu.id as turno_id, ca.deporte_id as deporte_id, ca.id as cancha_id,ca.filial_id as filial_id from turno tu
inner join cancha ca on ca.id = tu.cancha_id
inner join deporte de on de.id = ca.deporte_id
inner join tipo_cancha tc on tc.id = ca.tipo_cancha_id', @usuario, @filial, @deporte, @fecha_alquiler, ' order by tu.fecha_hora_desde desc');

    PREPARE stmt FROM @querys;
    EXECUTE stmt;
END$$

DELIMITER ;




USE `db_los_amigos`;
DROP procedure IF EXISTS `CREAR_USUARIO`;

DELIMITER $$
USE `db_los_amigos`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CREAR_USUARIO` (IN p_nombre varchar(45), IN p_apellido varchar(45), IN p_calle varchar(100), IN p_altura int, IN p_localidad_id int, IN p_telefono varchar(15), IN p_email varchar(100), IN p_fecha_nacimieto date, IN p_usuario varchar(45), IN p_clave varchar(45), IN p_perfil_codigo varchar(2))
BEGIN

INSERT INTO `db_los_amigos`.`usuario`
(`nombre`,
`apellido`,
`calle`,
`altura`,
`localidad_id`,
`telefono`,
`email`,
`fecha_nacimieto`,
`usuario`,
`clave`,
`rol_id`,
`activo`)
VALUES
(p_nombre ,
p_apellido ,
p_calle ,
p_altura ,
p_localidad_id ,
p_telefono ,
p_email ,
p_fecha_nacimieto ,
p_usuario ,
p_clave ,
(select id from rol where codigo = p_perfil_codigo),
1 );
END$$

DELIMITER ;














