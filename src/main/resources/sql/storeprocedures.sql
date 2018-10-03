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
 or ( p_fecha_desde between tu.fecha_hora_desde and tu.fecha_hora_hasta)
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `TRAER_ALQUILERES_USUARIO`(IN p_usuario_id int)
    COMMENT 'Trae las canchas alquiladas por el usuario'
BEGIN
select ca.codigo as 'cancha_codigo', tc.descripcion as 'tipo_cancha_descripcion', de.descripcion as 'deporte_descripcion', tu.fecha_hora_desde as 'fecha_hora_desde', tu.fecha_hora_hasta as 'fecha_hora_hasta' , 
case 
 when TIMESTAMPDIFF(minute,now(),tu.fecha_hora_desde) > 120 then true 
 else false end as 'puede_anular',tu.id as 'turno_id', ca.deporte_id as 'deporte_id', ca.id as 'cancha_id',ca.filial_id as 'filial_id' from turno tu
inner join cancha ca on ca.id = tu.cancha_id
inner join deporte de on de.id = ca.deporte_id
inner join tipo_cancha tc on tc.id = ca.tipo_cancha_id
where tu.usuario_id = p_usuario_id and tu.cancelado = 0
order by tu.fecha_hora_desde desc;
END$$

DELIMITER ;




