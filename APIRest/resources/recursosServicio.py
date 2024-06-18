from flask import request
from flask_restful import Resource
from http import HTTPStatus
from models.servicio import Servicio

class ServicioListResource(Resource):
    """ Responde a las peticiones /servicios """

    def get(self):
        """ Respuesta al método HTTP GET. Retorna todos los servicios """
        servicios = Servicio.query.all()
        datos = [servicio.data for servicio in servicios]
        return {'data': datos}, HTTPStatus.OK

    def post(self):
        """
        Respuesta al método HTTP POST.
        Espera recibir los datos de un servicio para guardarlo.
        Retorna el servicio creado.
        """
        datos = request.get_json()

        nombre_servicio = datos.get('nombre_servicio')
        if Servicio.query.filter_by(nombre_servicio=nombre_servicio).first():
            return {'message': 'Ya existe un servicio con ese nombre.'}, HTTPStatus.BAD_REQUEST

        servicio = Servicio(nombre_servicio=nombre_servicio)
        servicio.guardar()

        return servicio.data, HTTPStatus.CREATED

class ServicioResource(Resource):
    """ Responde a las peticiones /servicios/<int:id_servicio> """

    def get(self, id_servicio):
        """ Retorna los datos de un servicio específico """
        servicio = Servicio.get_by_id_servicio(id_servicio)

        if servicio is None:
            return {'message': 'Servicio no encontrado.'}, HTTPStatus.NOT_FOUND

        return servicio.data, HTTPStatus.OK

    def put(self, id_servicio):
        """ Actualiza los datos de un servicio """
        servicio = Servicio.get_by_id_servicio(id_servicio)

        if servicio is None:
            return {'message': 'Servicio no encontrado.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        servicio.nombre_servicio = datos.get('nombre_servicio')
        servicio.guardar()

        return servicio.data, HTTPStatus.OK

    def delete(self, id_servicio):
        """ Elimina un servicio """
        servicio = Servicio.get_by_id_servicio(id_servicio)

        if servicio is None:
            return {'message': 'Servicio no encontrado.'}, HTTPStatus.NOT_FOUND

        servicio.borrar()
        return {}, HTTPStatus.NO_CONTENT
