from flask import request
from flask_restful import Resource
from http import HTTPStatus
from models.pago import Pago

class PagoListResource(Resource):
    """ Responde a las peticiones /pagos """

    def get(self):
        """ Respuesta al método HTTP GET. Retorna todos los pagos """
        pagos = Pago.query.all()
        datos = [pago.data for pago in pagos]
        return {'data': datos}, HTTPStatus.OK

    def post(self):
        """
        Respuesta al método HTTP POST.
        Espera recibir los datos de un pago para guardarlo.
        Retorna el pago creado.
        """
        datos = request.get_json()

        nombre_pago = datos.get('nombre_pago')
        if Pago.query.filter_by(nombre_pago=nombre_pago).first():
            return {'message': 'Ya existe un pago con ese nombre.'}, HTTPStatus.BAD_REQUEST

        pago = Pago(fecha_pago=datos.get('fecha_pago'),
                                  importe=datos.get('importe'),
                                  id_usuario=datos.get('id_usuario'),
                                  id_servicio=datos.get('id_servicio'))
        pago.guardar()

        return pago.data, HTTPStatus.CREATED

class PagoResource(Resource):
    """ Responde a las peticiones /pagos/<int:id_pago> """

    def get(self, id_pago):
        """ Retorna los datos de un pago específico """
        pago = Pago.get_by_id_pago(id_pago)

        if pago is None:
            return {'message': 'Pago no encontrado.'}, HTTPStatus.NOT_FOUND

        return pago.data, HTTPStatus.OK

    def put(self, id_pago):
        """ Actualiza los datos de un pago """
        pago = Pago.get_by_id_pago(id_pago)

        if pago is None:
            return {'message': 'Pago no encontrado.'}, HTTPStatus.NOT_FOUND

        datos = request.get_json()
        pago.nombre_suscripcion = datos.get('nombre_suscripcion')
        pago.logo = datos.get('logo')
        pago.fecha_inicio = datos.get('fecha_inicio')
        pago.fecha_fin = datos.get('fecha_fin')
        pago.periodicidad = datos.get('periodicidad')
        pago.importe = datos.get('importe')
        pago.notas = datos.get('notas')
        pago.id_usuario = datos.get('id_usuario')
        pago.id_servicio = datos.get('id_servicio')
        pago.guardar()

        return pago.data, HTTPStatus.OK

    def delete(self, id_pago):
        """ Elimina un pago """
        pago = Pago.get_by_id_pago(id_pago)

        if pago is None:
            return {'message': 'Pago no encontrado.'}, HTTPStatus.NOT_FOUND

        pago.borrar()
        return {}, HTTPStatus.NO_CONTENT
