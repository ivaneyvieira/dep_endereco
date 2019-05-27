CREATE OR
  REPLACE VIEW layout_niveis AS
select nivel_id, GROUP_CONCAT(IF(ocupado = 'S', tipo_palet, '_') SEPARATOR '') as layout
from ocupacao_enderecos
GROUP BY nivel_id