CREATE TABLE IF NOT EXISTS destinos_turisticos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    ubicacion VARCHAR(255) NOT NULL
);

INSERT INTO destinos_turisticos (nombre, descripcion, ubicacion) VALUES
('Machu Picchu', 'Ciudadela inca ubicada en la región andina de Perú.', 'Cusco, Perú'),
('Gran Cañón', 'Impresionante garganta excavada por el río Colorado.', 'Arizona, EE.UU.'),
('Torre Eiffel', 'Famosa torre de hierro en el corazón de París.', 'París, Francia'),
('Gran Muralla China', 'Antigua fortificación que se extiende a lo largo del norte de China.', 'China'),
('Cataratas del Iguazú', 'Conjunto de impresionantes cascadas en la frontera entre Argentina y Brasil.', 'Misiones, Argentina'),
('Monte Everest', 'La montaña más alta del mundo.', 'Frontera entre Nepal y Tíbet'),
('Pirámides de Giza', 'Monumentales pirámides construidas por los antiguos egipcios.', 'Giza, Egipto'),
('Santorini', 'Isla griega famosa por sus casas blancas y vistas al mar.', 'Grecia'),
('Parque Nacional Kruger', 'Uno de los parques de safari más grandes de África.', 'Sudáfrica'),
('Chichén Itzá', 'Sitio arqueológico de la civilización maya.', 'Yucatán, México');