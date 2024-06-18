from extensions import db
from models.suscripcion import Suscripcion

class Usuario(db.Model):
    __tablename__ = 'Usuario'

    id_usuario = db.Column(db.Integer, primary_key=True)
    correo = db.Column(db.String(100), nullable=False)
    password = db.Column(db.String(255))

    @classmethod
    def get_by_correo(cls, correo):
        """
        Busca un usuario con el correo pasado como parámetro.
        Retorna un objeto Usuario si lo encuentra, None si no.
        """
        return cls.query.filter_by(correo=correo).first()

    @classmethod
    def get_by_id_usuario(cls, id_usuario):
        """
        Busca un usuario con el id pasado como parámetro.
        Retorna un objeto Usuario si lo encuentra, None si no.
        """
        return cls.query.filter_by(id_usuario=id_usuario).first()
        
    def get_suscripciones(self):
        """
        Retorna las suscripciones asociadas a este usuario.
        """
        return Suscripcion.query.filter_by(id_usuario=self.id_usuario).all()

    def guardar(self):
        """
        Envía el usuario a la base de datos.
        """
        db.session.add(self)
        db.session.commit()

    def borrar(self):
        """
        Borra el usuario de la base de datos.
        """
        db.session.delete(self)
        db.session.commit()
        
    def verificar_password(self, password):
        """
        Verifica si la contraseña proporcionada coincide con la almacenada.
        Retorna True si coinciden, False si no.
        """
        return self.password == password    

    @property
    def data(self):
        return {
            'id_usuario': self.id_usuario,
            'correo': self.correo,
            'password': self.password,
        }