import { useEffect, useState } from 'react';

const Entregas = () => {
  const [entregas, setEntregas] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [transportadoras, setTransportadoras] = useState([]);
  const [clienteId, setClienteId] = useState('');
  const [transportadoraId, setTransportadoraId] = useState('');
  const [descricao, setDescricao] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    fetchEntregas();
    fetchClientes();
    fetchTransportadoras();
  }, []);

  const fetchEntregas = async () => {
    try {
      const response = await fetch('/api/entregas');
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Fetch entregas erro:', response.status, errorText);
        setMensagem('Erro ao carregar entregas.');
        setEntregas([]);
        return;
      }
      const data = await response.json();
      setEntregas(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error(error);
      setMensagem('Erro ao carregar entregas.');
      setEntregas([]);
    }
  };

  const fetchClientes = async () => {
    try {
      const response = await fetch('/api/clientes');
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Fetch clientes erro:', response.status, errorText);
        return;
      }
      const data = await response.json();
      setClientes(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchTransportadoras = async () => {
    try {
      const response = await fetch('/api/transportadoras');
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Fetch transportadoras erro:', response.status, errorText);
        return;
      }
      const data = await response.json();
      setTransportadoras(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMensagem('');

    if (!clienteId || !transportadoraId) {
      setMensagem('Selecione cliente e transportadora para criar a entrega.');
      return;
    }

    setCarregando(true);
    try {
      const response = await fetch('/api/entregas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          clienteId: Number(clienteId),
          transportadoraId: Number(transportadoraId),
          descricao,
          enderecoOrigemId: null,
        }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        setMensagem(`Erro ao criar entrega: ${errorText}`);
        return;
      }

      const novaEntrega = await response.json();
      setEntregas((prev) => [...prev, novaEntrega]);
      setDescricao('');
      setMensagem('Entrega criada com sucesso.');
    } catch (error) {
      console.error(error);
      setMensagem('Falha ao criar entrega.');
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className="row gy-4">
      <div className="col-12">
        <div className="card overflow-hidden shadow-sm mb-4">
          <div className="row g-0 align-items-center">
            <div className="col-md-6">
              <img
                src="https://images.unsplash.com/photo-1521791136064-7986c2920216?auto=format&fit=crop&w=1200&q=80"
                className="img-fluid rounded-start"
                alt="Entrega rápida"
              />
            </div>
            <div className="col-md-6">
              <div className="card-body">
                <h2 className="card-title">Entregas</h2>
                <p className="card-text text-muted">
                  Crie uma entrega vinculada a um cliente existente e escolha a transportadora.
                </p>
                <p className="card-text">
                  A lista abaixo mostra todas as entregas registradas e as relações com cliente e transportadora.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="col-lg-5">
        <div className="card shadow-sm h-100">
          <div className="card-body">
            <h4 className="card-title mb-3">Nova Entrega</h4>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Cliente</label>
                <select className="form-select" value={clienteId} onChange={(e) => setClienteId(e.target.value)}>
                  <option value="">Selecione um cliente</option>
                  {clientes.map((cliente) => (
                    <option key={cliente.id} value={cliente.id}>
                      {cliente.nome} ({cliente.endereco?.cidade || 'sem cidade'})
                    </option>
                  ))}
                </select>
              </div>

              <div className="mb-3">
                <label className="form-label">Transportadora</label>
                <select className="form-select" value={transportadoraId} onChange={(e) => setTransportadoraId(e.target.value)}>
                  <option value="">Selecione uma transportadora</option>
                  {transportadoras.map((transportadora) => (
                    <option key={transportadora.id} value={transportadora.id}>
                      {transportadora.nome}
                    </option>
                  ))}
                </select>
              </div>

              <div className="mb-3">
                <label className="form-label">Descrição</label>
                <textarea
                  className="form-control"
                  rows="3"
                  value={descricao}
                  onChange={(e) => setDescricao(e.target.value)}
                  placeholder="Descrição da entrega (opcional)"
                />
              </div>

              <button type="submit" className="btn btn-primary" disabled={carregando}>
                {carregando ? 'Salvando...' : 'Criar Entrega'}
              </button>
            </form>
            {mensagem && <div className="alert alert-info mt-3">{mensagem}</div>}
          </div>
        </div>
      </div>

      <div className="col-lg-7">
        <div className="card shadow-sm">
          <div className="card-body">
            <h4 className="card-title mb-3">Entregas Cadastradas</h4>
            <div className="table-responsive">
              <table className="table table-hover align-middle">
                <thead className="table-light">
                  <tr>
                    <th>ID</th>
                    <th>Status</th>
                    <th>Cliente</th>
                    <th>Transportadora</th>
                    <th>Descrição</th>
                    <th>Origem</th>
                  </tr>
                </thead>
                <tbody>
                  {entregas.length === 0 ? (
                    <tr>
                      <td colSpan="6" className="text-center text-muted py-4">
                        Nenhuma entrega encontrada.
                      </td>
                    </tr>
                  ) : (
                    entregas.map((entrega) => (
                      <tr key={entrega.id}>
                        <td>{entrega.id}</td>
                        <td>{entrega.status || '—'}</td>
                        <td>{entrega.cliente?.nome || '—'}</td>
                        <td>{entrega.transportadora?.nome || '—'}</td>
                        <td>{entrega.descricao || '—'}</td>
                        <td>
                          {entrega.enderecoOrigem?.rua
                            ? `${entrega.enderecoOrigem.rua}, ${entrega.enderecoOrigem.cidade || ''}`
                            : '—'}
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Entregas;
