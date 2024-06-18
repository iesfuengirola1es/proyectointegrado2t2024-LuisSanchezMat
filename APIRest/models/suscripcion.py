from extensions import db

class Suscripcion(db.Model):
    __tablename__ = 'Suscripcion'

    id_suscripcion = db.Column(db.Integer, primary_key=True)
    nombre_suscripcion = db.Column(db.String(100))
    logo = db.Column(db.Text)
    fecha_inicio = db.Column(db.Date)
    fecha_fin = db.Column(db.Date)
    periodicidad = db.Column(db.Enum('No', 'Semanal', 'Mensual', 'Trimestral', 'Anual'))
    importe = db.Column(db.Numeric(10, 2))
    notas = db.Column(db.String(255))
    id_usuario = db.Column(db.Integer, db.ForeignKey('Usuario.id_usuario'))
    id_servicio = db.Column(db.Integer, db.ForeignKey('Servicio.id_servicio'))

    usuario = db.relationship('Usuario', backref='suscripciones')
    servicio = db.relationship('Servicio', backref='suscripciones')

    @classmethod
    def get_by_id_suscripcion(cls, id_suscripcion):
        """
        Busca una suscripcion con el id pasado como parámetro.
        Retorna un objeto Suscripcion si lo encuentra, None si no.
        """
        return cls.query.filter_by(id_suscripcion=id_suscripcion).first()

    @classmethod
    def get_by_nombre_suscripcion(cls, nombre_suscripcion):
        """
        Busca una suscripcion con el nombre pasado como parámetro.
        Retorna un objeto Suscripcion si lo encuentra, None si no.
        """
        return cls.query.filter_by(nombre_suscripcion=nombre_suscripcion).first()


    def guardar(self):
        """
        Envía la suscripcion a la base de datos.
        """
        db.session.add(self)
        db.session.commit()

    def borrar(self):
        """
        Borra la suscripcion de la base de datos.
        """
        db.session.delete(self)
        db.session.commit()

    @property
    def data(self):
        return {
            'id_suscripcion': self.id_suscripcion,
            'nombre_suscripcion': self.nombre_suscripcion,
            'logo': self.logo,
            'fecha_inicio': self.fecha_inicio.isoformat(),
            'fecha_fin': self.fecha_fin.isoformat(),
            'periodicidad': self.periodicidad,
            'importe': float(self.importe),
            'notas': self.notas,
            'id_usuario': self.id_usuario,
            'id_servicio': self.id_servicio
        }