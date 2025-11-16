package school.sptech.EncantoPersonalizados.service;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import school.sptech.EncantoPersonalizados.entities.Pedido;
import school.sptech.EncantoPersonalizados.entities.PedidoStatusPedido;
import school.sptech.EncantoPersonalizados.repository.PedidoStatusPedidoRepository;

import java.util.List;

@Service
public class PedidoStatusPedidoService {

    private final PedidoStatusPedidoRepository  pedidoStatusPedidoRepository;
    private final PedidoService pedidoService;
    private final StatusPedidoService statusPedidoService;


    public PedidoStatusPedidoService(PedidoStatusPedidoRepository pedidoStatusPedidoRepository, PedidoService pedidoService, StatusPedidoService statusPedidoService) {
        this.pedidoStatusPedidoRepository = pedidoStatusPedidoRepository;
        this.pedidoService = pedidoService;
        this.statusPedidoService = statusPedidoService;
    }

    public PedidoStatusPedido salvar(PedidoStatusPedido entity){
        // verificar se o status existe
        if(statusPedidoService.findById(entity.getStatus().getId()) == null){
            throw new RuntimeException("StatusPedido não encontrado");
        }

        // verificar se o pedido existe
        Pedido pedido = pedidoService.findById(entity.getPedido().getId());
        if(pedido == null){
            throw new RuntimeException("Pedido não encontrado");
        }

        // desativar o status atual do pedido
        List<PedidoStatusPedido> statusAtuais = pedidoStatusPedidoRepository.findStatusAtualByPedidoId(pedido.getId());
        for (PedidoStatusPedido statusAtual : statusAtuais) {
            statusAtual.setStatusAtual(false);
            pedidoStatusPedidoRepository.save(statusAtual);
        }



        return pedidoStatusPedidoRepository.save(entity);
    }

    public PedidoStatusPedido findById(Integer id){
        return pedidoStatusPedidoRepository.findById(id).orElse(null);
    }
}
