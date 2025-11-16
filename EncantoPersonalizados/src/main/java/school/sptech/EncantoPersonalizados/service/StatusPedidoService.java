package school.sptech.EncantoPersonalizados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.dto.statusPedido.StatusPedidoReordenacaoDto;
import school.sptech.EncantoPersonalizados.entities.StatusPedido;
import school.sptech.EncantoPersonalizados.repository.StatusPedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusPedidoService {

    private final StatusPedidoRepository statusPedidoRepository;

    public StatusPedidoService(StatusPedidoRepository statusPedidoRepository) {
        this.statusPedidoRepository = statusPedidoRepository;
    }

    public Page<StatusPedido> listar(Integer page, Boolean ativo){
        Pageable pageable = PageRequest.of(page, 10);

        Page<StatusPedido> statusPedidoPage = statusPedidoRepository.filtrar(ativo, pageable);

        return statusPedidoPage;
    }

    public StatusPedido store(StatusPedido statusPedido){
        statusPedido.setCreatedAt(LocalDateTime.now());
        return statusPedidoRepository.save(statusPedido);
    }

    public StatusPedido update(StatusPedido statusPedido, Integer id){
        StatusPedido existente = statusPedidoRepository.findById(id).orElseThrow();
        existente.setStatus(statusPedido.getStatus());
        existente.setCor(statusPedido.getCor());
        existente.setOrdemKanban(statusPedido.getOrdemKanban());
        existente.setUpdatedAt(LocalDateTime.now());


        return statusPedidoRepository.save(existente);
    }

    public void mudarEstado(Integer id){
        StatusPedido existente = statusPedidoRepository.findById(id).orElseThrow();
        existente.setAtivo(!existente.isAtivo());
        statusPedidoRepository.save(existente);
    }

    public void reordenarKanban(List<StatusPedidoReordenacaoDto>   statusPedidos){
        for (StatusPedidoReordenacaoDto dto : statusPedidos) {
            StatusPedido existente = statusPedidoRepository.findById(dto.id()).orElseThrow();
            existente.setOrdemKanban(dto.novaOrdemKanban());
            statusPedidoRepository.save(existente);
        }

    }

    public StatusPedido findById(Integer id){
        return statusPedidoRepository.findById(id).orElse(null);
    }

    public StatusPedido findFirstOfKanbanOrder(){
        return statusPedidoRepository.findFirstByOrderByOrdemKanbanAsc().orElse(null);
    }

}
