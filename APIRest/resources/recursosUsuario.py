from flask import request
from flask_restful import Resource
from http import HTTPStatus
from models.usuario import Usuario
from models.suscripcion import Suscripcion  # Asegúrate de importar el modelo Suscripcion

class UsuarioListResource(Resource):
    """ Responde a las peticiones /usuarios """
    def get(self):
        """ Respuesta al método HTTP GET. Retorna todos los usuarios """
        usuarios = Usuario.query.all()
        datos = [usuario.data for usuario in usuarios]
        return {'data': datos}, HTTPStatus.OK

    def post(self):
        """
        Respuesta al método HTTP POST.
        Espera recibir los datos de un usuario para guardarlo.
        Retorna el usuario creado.
        """
        datos = request.get_json()

        correo = datos.get('correo')
        if Usuario.query.filter_by(correo=correo).first():
            return {'message': 'Ya existe un usuario con ese correo.'}, HTTPStatus.BAD_REQUEST

        usuario = Usuario(correo=correo,password=datos.get('password'))
        usuario.guardar()

        return usuario.data, HTTPStatus.CREATED

class SuscripcionesUsuarioResource(Resource):
    """ Responde a las peticiones /usuarios/<int:id_usuario>/suscripciones """

    def get(self, id_usuario):
        """ Retorna las suscripciones asociadas a un usuario específico """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripciones = usuario.get_suscripciones()
        suscripciones_data = [suscripcion.data for suscripcion in suscripciones]

        return {'suscripciones': suscripciones_data}, HTTPStatus.OK

    def post(self, id_usuario):
        """ Crea una nueva suscripción para un usuario """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        suscripcion = Suscripcion(nombre=datos.get('nombre'),
                                  fecha_inicio=datos.get('fecha_inicio'),
                                  fecha_fin=datos.get('fecha_fin'),
                                  importe=datos.get('importe'),
                                  notas=datos.get('notas'),
                                  periodicidad=datos.get('periodicidad'),
                                  id_usuario=id_usuario)

        suscripcion.guardar()

        return suscripcion.data, HTTPStatus.CREATED

    def put(self, id_usuario, id_suscripcion):
        """ Actualiza los datos de una suscripción específica de un usuario """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None or suscripcion.id_usuario != id_usuario:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        suscripcion.nombre = datos.get('nombre', suscripcion.nombre)
        suscripcion.fecha_inicio = datos.get('fecha_inicio', suscripcion.fecha_inicio)
        suscripcion.fecha_fin = datos.get('fecha_fin', suscripcion.fecha_fin)
        suscripcion.importe = datos.get('importe', suscripcion.importe)
        suscripcion.notas = datos.get('notas', suscripcion.notas)
        suscripcion.periodicidad = datos.get('periodicidad', suscripcion.periodicidad)

        suscripcion.guardar()

        return suscripcion.data, HTTPStatus.OK

    def delete(self, id_usuario, id_suscripcion):
        """ Elimina una suscripción específica de un usuario """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None or suscripcion.id_usuario != id_usuario:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        suscripcion.borrar()

        return {}, HTTPStatus.NO_CONTENT


class LoginResource(Resource):
    """ Responde a las peticiones /login """

    def post(self):
        """ Respuesta al método HTTP POST. """
        datos = request.get_json()

        correo = datos.get('correo')
        password = datos.get('password')

        if not correo or not password:
            return {'message': 'Correo y contraseña son requeridos'}, HTTPStatus.BAD_REQUEST

        usuario = Usuario.query.filter_by(correo=correo).first()

        if usuario and usuario.verificar_password(password):
            return {'message': 'Inicio de sesión exitoso', 'id_usuario': usuario.id_usuario}, HTTPStatus.OK
        else:
            return {'message': 'Credenciales incorrectas'}, HTTPStatus.UNAUTHORIZED


class UsuarioResource(Resource):
    """ Responde a las peticiones /usuarios/int:id_usuario """

    def get(self, id_usuario):
        """ Retorna los datos de un usuario específico """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        return usuario.data, HTTPStatus.OK

    def put(self, id_usuario):
        """ Actualiza los datos de un usuario """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        usuario.correo = datos.get('correo')
        usuario.password = datos.get('password')
        usuario.guardar()

        return usuario.data, HTTPStatus.OK

    def delete(self, id_usuario):
        """ Elimina un usuario """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        usuario.borrar()
        return {}, HTTPStatus.NO_CONTENT





class SuscripcionEditResource(Resource):
    """ Responde a las peticiones /usuarios/<int:id_usuario>/suscripciones/<int:id_suscripcion> """

    def get(self, id_usuario, id_suscripcion):
        """ Retorna los detalles de una suscripción específica """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None or suscripcion.id_usuario != id_usuario:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        return suscripcion.data, HTTPStatus.OK

    def put(self, id_usuario, id_suscripcion):
        """ Actualiza los datos de una suscripción específica """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None or suscripcion.id_usuario != id_usuario:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        suscripcion.nombre = datos.get('nombre', suscripcion.nombre)
        suscripcion.fecha_inicio = datos.get('fecha_inicio', suscripcion.fecha_inicio)
        suscripcion.fecha_fin = datos.get('fecha_fin', suscripcion.fecha_fin)
        suscripcion.importe = datos.get('importe', suscripcion.importe)
        suscripcion.notas = datos.get('notas', suscripcion.notas)
        suscripcion.periodicidad = datos.get('periodicidad', suscripcion.periodicidad)

        suscripcion.guardar()

        return suscripcion.data, HTTPStatus.OK

    def delete(self, id_usuario, id_suscripcion):
        """ Elimina una suscripción específica """
        usuario = Usuario.get_by_id_usuario(id_usuario)

        if usuario is None:
            return {'message': 'Usuario no encontrado.'}, HTTPStatus.NOT_FOUND

        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None or suscripcion.id_usuario != id_usuario:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        suscripcion.borrar()

        return {}, HTTPStatus.NO_CONTENT
