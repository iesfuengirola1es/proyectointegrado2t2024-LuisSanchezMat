from extensions import db

class Pago(db.Model):
    __tablename__ = 'Pago'

    id_pago = db.Column(db.Integer, primary_key=True)
    fecha_pago = db.Column(db.Date)
    importe = db.Column(db.Numeric(10, 2))
    id_usuario = db.Column(db.Integer, db.ForeignKey('Usuario.id_usuario'))
    id_servicio = db.Column(db.Integer, db.ForeignKey('Servicio.id_servicio'))

    usuario = db.relationship('Usuario', backref='pagos')
    servicio = db.relationship('Servicio', backref='pagos_realizados')

    @classmethod
    def get_by_id_pago(cls, id_pago):
        """
        Busca un pago por su ID.
        Retorna un objeto Pago si lo encuentra, None si no.
        """
        return cls.query.filter_by(id_pago=id_pago).first()

    def guardar(self):
        """
        Guarda el pago en la base de datos.
        """
        db.session.add(self)
        db.session.commit()

    def borrar(self):
        """
        Elimina el pago de la base de datos.
        """
        db.session.delete(self)
        db.session.commit()

    @property
    def data(self):
        return {
            'id_pago': self.id_pago,
            'fecha_pago': self.fecha_pago.isoformat(),
            'importe': float(self.importe),
            'id_usuario': self.id_usuario,
            'id_servicio': self.id_servicio
        }
