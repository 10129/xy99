package xy99.core.mybatis.handler;


import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BlobTypeHandler extends BaseTypeHandler<String> {
    private static final String DEFAULT_CHARSET = "utf-8";

    public BlobTypeHandler() {
    }

    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ByteArrayInputStream bis;
        try {
            bis = new ByteArrayInputStream(parameter.getBytes("utf-8"));
        } catch (UnsupportedEncodingException var7) {
            throw new RuntimeException("Blob Encoding Error!");
        }

        ps.setBinaryStream(i, bis, parameter.length());
    }

    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        try {
            return new String(returnValue, "utf-8");
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }

    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        try {
            return new String(returnValue, "utf-8");
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }

    public String getNullableResult(ResultSet rs, int index) throws SQLException {
        Blob blob = rs.getBlob(index);
        byte[] returnValue = null;
        if(null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        }

        try {
            return new String(returnValue, "utf-8");
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }
}
