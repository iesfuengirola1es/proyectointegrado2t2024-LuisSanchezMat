"""empty message

Revision ID: b1691760deec
Revises: 
Create Date: 2024-06-16 11:14:07.737331

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = 'b1691760deec'
down_revision = None
branch_labels = None
depends_on = None


def upgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('Servicio',
    sa.Column('id_servicio', sa.Integer(), nullable=False),
    sa.Column('nombre_servicio', sa.String(length=100), nullable=True),
    sa.PrimaryKeyConstraint('id_servicio')
    )
    op.create_table('Usuario',
    sa.Column('id_usuario', sa.Integer(), nullable=False),
    sa.Column('correo', sa.String(length=100), nullable=False),
    sa.Column('password', sa.String(length=255), nullable=True),
    sa.PrimaryKeyConstraint('id_usuario')
    )
    op.create_table('Pago',
    sa.Column('id_pago', sa.Integer(), nullable=False),
    sa.Column('fecha_pago', sa.Date(), nullable=True),
    sa.Column('importe', sa.Numeric(precision=10, scale=2), nullable=True),
    sa.Column('id_usuario', sa.Integer(), nullable=True),
    sa.Column('id_servicio', sa.Integer(), nullable=True),
    sa.ForeignKeyConstraint(['id_servicio'], ['Servicio.id_servicio'], ),
    sa.ForeignKeyConstraint(['id_usuario'], ['Usuario.id_usuario'], ),
    sa.PrimaryKeyConstraint('id_pago')
    )
    op.create_table('Suscripcion',
    sa.Column('id_suscripcion', sa.Integer(), nullable=False),
    sa.Column('nombre_suscripcion', sa.String(length=100), nullable=True),
    sa.Column('logo', sa.String(length=255), nullable=True),
    sa.Column('fecha_inicio', sa.Date(), nullable=True),
    sa.Column('fecha_fin', sa.Date(), nullable=True),
    sa.Column('periodicidad', sa.Enum('No', 'Semanal', 'Mensual', 'Trimestral', 'Anual'), nullable=True),
    sa.Column('importe', sa.Numeric(precision=10, scale=2), nullable=True),
    sa.Column('notas', sa.String(length=255), nullable=True),
    sa.Column('id_usuario', sa.Integer(), nullable=True),
    sa.Column('id_servicio', sa.Integer(), nullable=True),
    sa.ForeignKeyConstraint(['id_servicio'], ['Servicio.id_servicio'], ),
    sa.ForeignKeyConstraint(['id_usuario'], ['Usuario.id_usuario'], ),
    sa.PrimaryKeyConstraint('id_suscripcion')
    )
    # ### end Alembic commands ###


def downgrade():
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('Suscripcion')
    op.drop_table('Pago')
    op.drop_table('Usuario')
    op.drop_table('Servicio')
    # ### end Alembic commands ###