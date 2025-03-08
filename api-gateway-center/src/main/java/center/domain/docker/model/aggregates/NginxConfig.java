package center.domain.docker.model.aggregates;

import center.domain.docker.model.vo.LocationVO;
import center.domain.docker.model.vo.UpstreamVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.docker.model.aggregates
 * @className: NginxConfig
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class NginxConfig {
    private String applicationName;
    private String nginxName;
    private String localNginxPath;
    private String remoteNginxPath;
    private List<UpstreamVO> upstreamList;
    private List<LocationVO> locationList;
    public NginxConfig(List<UpstreamVO> upstreamList, List<LocationVO> locationList) {
        this.applicationName = "api-gateway-center";
        this.nginxName = "Nginx";
        this.localNginxPath = "/api-gateway/api-gateway-center/doc/data/nginx/nginx.conf";
        this.remoteNginxPath = "/etc/nginx/";
        this.upstreamList = upstreamList;
        this.locationList = locationList;
    }

}
