from flask import request
from flask_restful import Resource
from http import HTTPStatus
from models.suscripcion import Suscripcion

class SuscripcionListResource(Resource):
    """ Responde a las peticiones /suscripciones """

    def get(self):
        """ Respuesta al método HTTP GET. Retorna todas las suscripciones """
        suscripciones = Suscripcion.query.all()
        datos = [suscripcion.data for suscripcion in suscripciones]
        return {'data': datos}, HTTPStatus.OK

    def post(self):
        """
        Respuesta al método HTTP POST.
        Espera recibir los datos de una suscripción para guardarla.
        Retorna la suscripción creada.
        """
        datos = request.get_json()

        nombre_suscripcion = datos.get('nombre_suscripcion')
        id_usuario = datos.get('id_usuario')
        if Suscripcion.query.filter_by(nombre_suscripcion=nombre_suscripcion,id_usuario=id_usuario).first():
            return {'message': 'Ya existe una suscripción con ese nombre.'}, HTTPStatus.BAD_REQUEST

        suscripcion = Suscripcion(nombre_suscripcion=nombre_suscripcion,
                                  logo=datos.get('logo'),
                                  fecha_inicio=datos.get('fecha_inicio'),
                                  fecha_fin=datos.get('fecha_fin'),
                                  periodicidad=datos.get('periodicidad'),
                                  importe=datos.get('importe'),
                                  notas=datos.get('notas'),
                                  id_usuario=datos.get('id_usuario'),
                                  id_servicio=datos.get('id_servicio'))
        suscripcion.guardar()

        return suscripcion.data, HTTPStatus.CREATED

class SuscripcionResource(Resource):
    """ Responde a las peticiones /suscripciones/<int:id_suscripcion> """

    def get(self, id_suscripcion):
        """ Retorna los datos de una suscripción específica """
        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        return suscripcion.data, HTTPStatus.OK

    def put(self, id_suscripcion):
        """ Actualiza los datos de una suscripción """
        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        suscripcion.nombre_suscripcion = datos.get('nombre_suscripcion')
        suscripcion.logo = datos.get('logo')
        suscripcion.fecha_inicio = datos.get('fecha_inicio')
        suscripcion.fecha_fin = datos.get('fecha_fin')
        suscripcion.periodicidad = datos.get('periodicidad')
        suscripcion.importe = datos.get('importe')
        suscripcion.notas = datos.get('notas')
        suscripcion.id_usuario = datos.get('id_usuario')
        suscripcion.id_servicio = datos.get('id_servicio')
        suscripcion.guardar()

        return suscripcion.data, HTTPStatus.OK

    def delete(self, id_suscripcion):
        """ Elimina una suscripción """
        suscripcion = Suscripcion.get_by_id_suscripcion(id_suscripcion)

        if suscripcion is None:
            return {'message': 'Suscripción no encontrada.'}, HTTPStatus.NOT_FOUND

        suscripcion.borrar()
        return {}, HTTPStatus.NO_CONTENT
