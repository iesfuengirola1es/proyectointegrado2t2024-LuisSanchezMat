from extensions import db

class Servicio(db.Model):
    __tablename__ = 'Servicio'

    id_servicio = db.Column(db.Integer, primary_key=True)
    nombre_servicio = db.Column(db.String(100))

    @classmethod
    def get_by_id_servicio(cls, id_servicio):
        """
        Busca un servicio por su ID.
        Retorna un objeto Servicio si lo encuentra, None si no.
        """
        return cls.query.filter_by(id_servicio=id_servicio).first()

    @classmethod
    def get_by_nombre_servicio(cls, nombre_servicio):
        """
        Busca un servicio con el nombre pasado como parámetro.
        Retorna un objeto Servicio si lo encuentra, None si no.
        """
        return cls.query.filter_by(nombre_servicio=nombre_servicio).first()

    def guardar(self):
        """
        Guarda el servicio en la base de datos.
        """
        db.session.add(self)
        db.session.commit()

    def borrar(self):
        """
        Elimina el servicio de la base de datos.
        """
        db.session.delete(self)
        db.session.commit()

    @property
    def data(self):
        return {
            'id_servicio': self.id_servicio,
            'nombre_servicio': self.nombre_servicio,
        }
