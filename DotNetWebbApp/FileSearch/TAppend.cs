/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;


/// <summary>
/// An Append object is used to specify the parameters for performing the append operation.
/// </summary>
#if !SILVERLIGHT
[Serializable]
#endif
public partial class TAppend : TBase
{
  private byte[] _table;
  private byte[] _row;
  private List<byte[]> _columns;
  private List<byte[]> _values;

  public byte[] Table
  {
    get
    {
      return _table;
    }
    set
    {
      __isset.table = true;
      this._table = value;
    }
  }

  public byte[] Row
  {
    get
    {
      return _row;
    }
    set
    {
      __isset.row = true;
      this._row = value;
    }
  }

  public List<byte[]> Columns
  {
    get
    {
      return _columns;
    }
    set
    {
      __isset.columns = true;
      this._columns = value;
    }
  }

  public List<byte[]> Values
  {
    get
    {
      return _values;
    }
    set
    {
      __isset.values = true;
      this._values = value;
    }
  }


  public Isset __isset;
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public struct Isset {
    public bool table;
    public bool row;
    public bool columns;
    public bool values;
  }

  public TAppend() {
  }

  public void Read (TProtocol iprot)
  {
    TField field;
    iprot.ReadStructBegin();
    while (true)
    {
      field = iprot.ReadFieldBegin();
      if (field.Type == TType.Stop) { 
        break;
      }
      switch (field.ID)
      {
        case 1:
          if (field.Type == TType.String) {
            Table = iprot.ReadBinary();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 2:
          if (field.Type == TType.String) {
            Row = iprot.ReadBinary();
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 3:
          if (field.Type == TType.List) {
            {
              Columns = new List<byte[]>();
              TList _list17 = iprot.ReadListBegin();
              for( int _i18 = 0; _i18 < _list17.Count; ++_i18)
              {
                byte[] _elem19 = null;
                _elem19 = iprot.ReadBinary();
                Columns.Add(_elem19);
              }
              iprot.ReadListEnd();
            }
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        case 4:
          if (field.Type == TType.List) {
            {
              Values = new List<byte[]>();
              TList _list20 = iprot.ReadListBegin();
              for( int _i21 = 0; _i21 < _list20.Count; ++_i21)
              {
                byte[] _elem22 = null;
                _elem22 = iprot.ReadBinary();
                Values.Add(_elem22);
              }
              iprot.ReadListEnd();
            }
          } else { 
            TProtocolUtil.Skip(iprot, field.Type);
          }
          break;
        default: 
          TProtocolUtil.Skip(iprot, field.Type);
          break;
      }
      iprot.ReadFieldEnd();
    }
    iprot.ReadStructEnd();
  }

  public void Write(TProtocol oprot) {
    TStruct struc = new TStruct("TAppend");
    oprot.WriteStructBegin(struc);
    TField field = new TField();
    if (Table != null && __isset.table) {
      field.Name = "table";
      field.Type = TType.String;
      field.ID = 1;
      oprot.WriteFieldBegin(field);
      oprot.WriteBinary(Table);
      oprot.WriteFieldEnd();
    }
    if (Row != null && __isset.row) {
      field.Name = "row";
      field.Type = TType.String;
      field.ID = 2;
      oprot.WriteFieldBegin(field);
      oprot.WriteBinary(Row);
      oprot.WriteFieldEnd();
    }
    if (Columns != null && __isset.columns) {
      field.Name = "columns";
      field.Type = TType.List;
      field.ID = 3;
      oprot.WriteFieldBegin(field);
      {
        oprot.WriteListBegin(new TList(TType.String, Columns.Count));
        foreach (byte[] _iter23 in Columns)
        {
          oprot.WriteBinary(_iter23);
        }
        oprot.WriteListEnd();
      }
      oprot.WriteFieldEnd();
    }
    if (Values != null && __isset.values) {
      field.Name = "values";
      field.Type = TType.List;
      field.ID = 4;
      oprot.WriteFieldBegin(field);
      {
        oprot.WriteListBegin(new TList(TType.String, Values.Count));
        foreach (byte[] _iter24 in Values)
        {
          oprot.WriteBinary(_iter24);
        }
        oprot.WriteListEnd();
      }
      oprot.WriteFieldEnd();
    }
    oprot.WriteFieldStop();
    oprot.WriteStructEnd();
  }

  public override string ToString() {
    StringBuilder sb = new StringBuilder("TAppend(");
    sb.Append("Table: ");
    sb.Append(Table);
    sb.Append(",Row: ");
    sb.Append(Row);
    sb.Append(",Columns: ");
    sb.Append(Columns);
    sb.Append(",Values: ");
    sb.Append(Values);
    sb.Append(")");
    return sb.ToString();
  }

}

